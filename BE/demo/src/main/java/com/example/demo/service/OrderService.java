/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.ENUMS.OrderStatus;
import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Address;
import com.example.demo.model.Cart;
import com.example.demo.model.CartDetail;
import com.example.demo.model.Coupon;
import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.Payment;
import com.example.demo.model.ProductDetail;
import com.example.demo.model.User;
import com.example.demo.respository.CouponRepository;
import com.example.demo.respository.OrderRepository;
import com.example.demo.respository.PaymentRepository;
import com.example.demo.respository.ProductDetailRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.respository.UserRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CartService cartService;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final PaymentRepository paymentRepository;

    public Order addCoupon(Integer Id, String code) {
        // Lấy đơn hàng dựa trên ID
        Order order = getOrder(Id);

        // Lấy coupon từ mã code
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.COUPON_NOT_FOUND));

        // Kiểm tra xem coupon có còn hiệu lực không
        if (!coupon.isExpired() && coupon.isActive()) {
            // Thêm coupon vào danh sách coupon của đơn hàng
            order.getCoupons().add(coupon);

            // Tính toán giảm giá dựa trên tổng giá trị đơn hàng và coupon
            BigDecimal discount = calculateDiscount(order, coupon);

            // Cập nhật giá trị tổng đơn hàng sau khi áp dụng giảm giá
            BigDecimal totalPrice = getTotalPrice(new ArrayList<>(order.getOrderDetail()));
            BigDecimal finalPrice = totalPrice.subtract(discount);
            order.setTotal(finalPrice);  // Giả sử trường 'total' là giá trị cuối cùng của đơn hàng
        }

        // Lưu lại đơn hàng đã cập nhật và trả về
        return orderRepository.save(order);
    }

    public Order create(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("CHOXACNHAN");
        order.setDateCreate(new Date());
        return order;
    }

    public OrderResponse placeOrder(OrderRequest request) {
        // Lấy giỏ hàng của người dùng
        Cart cart = cartService.getCartByUserId(request.getUserId());
        log.info(cart.getId() + "Vu");

        // Tạo đơn hàng từ giỏ hàng
        Order order = create(cart);
        order.setAddress(request.getAddress());

        // Tạo chi tiết đơn hàng từ các sản phẩm trong giỏ hàng
        List<OrderDetail> orderItemList = createOrderDetails(order, cart);
        order.setOrderDetail(new HashSet<>(orderItemList));

        // Tính tổng giá trị đơn hàng
        BigDecimal total = getTotalPrice(orderItemList);
        order.setTotal(total);

        // Lấy phương thức thanh toán
        Payment payment = paymentRepository.findByName(request.getPaymentMethod())
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PAYMENT_NOT_FOUND));
        order.setPayment(payment);

        // Lưu đơn hàng vào cơ sở dữ liệu và xóa giỏ hàng
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        // Trả về thông tin đơn hàng đã tạo
        return orderMapper.tOrderResponse(order);
    }

    private List<OrderDetail> createOrderDetails(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            ProductDetail product = cartItem.getProductDetail();

            // Kiểm tra xem số lượng sản phẩm có đủ để bán không
            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new WebErrorConfig(ErrorCode.NOT_ENOUGH_VALUE);
            }

            // Giảm số lượng sản phẩm trong kho sau khi đã bán
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productDetailRepository.save(product);

            // Tạo chi tiết đơn hàng
            return new OrderDetail(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal getTotalPrice(List<OrderDetail> orderDetails) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderDetail orderDetail : orderDetails) {
            BigDecimal itemTotal = orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getQuantity()));
            total = total.add(itemTotal);
        }
        return total;
    }

    public Order getOrder(Integer Id) {
        return orderRepository.findById(Id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.ORDER_NOT_FOUND));
    }

    private BigDecimal calculateDiscount(Order order, Coupon coupon) {
        // Lấy tổng giá trị đơn hàng
        BigDecimal totalPrice = getTotalPrice(new ArrayList<>(order.getOrderDetail()));

        // Chuyển đổi minPrice và maxPrice của coupon sang BigDecimal để so sánh chính xác
        BigDecimal minPrice = BigDecimal.valueOf(coupon.getMinPrice());
        BigDecimal maxPrice = BigDecimal.valueOf(coupon.getMaxPrice());

        // Kiểm tra nếu tổng giá trị đơn hàng lớn hơn minPrice của coupon
        if (totalPrice.compareTo(minPrice) > 0) {
            // Tính toán giá trị giảm giá
            BigDecimal discount = totalPrice.multiply(coupon.getValue());

            // Nếu giá trị giảm giá vượt quá maxPrice, áp dụng maxPrice làm giảm giá
            if (discount.compareTo(maxPrice) > 0) {
                return maxPrice;
            }
            return discount;
        }

        // Nếu không thỏa mãn điều kiện, trả về giá trị giảm giá bằng 0
        return BigDecimal.ZERO;
    }

    public List<OrderResponse> getOrderByUser(String username) {
        // Tìm người dùng từ username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

        // Lấy tất cả các đơn hàng của người dùng đó
        List<Order> orders = orderRepository.findOrderByUser(user);

        // Ánh xạ các đơn hàng sang OrderResponse
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(orderMapper.tOrderResponse(order));
        }

        // Trả về danh sách OrderResponse
        return orderResponses;
    }

    public OrderResponse updateOrderStatus(Integer orderId, String newStatus) {
        // Lấy đơn hàng từ ID
        Order order = getOrder(orderId);

        // Kiểm tra trạng thái mới có hợp lệ không
        try {
            // Kiểm tra xem trạng thái mới có phải là một giá trị hợp lệ trong enum OrderStatus không
            OrderStatus status = OrderStatus.valueOf(newStatus);
            order.setStatus(status.name());  // Cập nhật trạng thái của đơn hàng
        } catch (IllegalArgumentException e) {
            // Nếu không phải là giá trị hợp lệ, ném ra một lỗi hoặc xử lý tùy ý
            throw new WebErrorConfig(ErrorCode.INVALID_ORDER_STATUS);
        }

        // Lưu lại đơn hàng sau khi thay đổi trạng thái
        orderRepository.save(order);
        return orderMapper.tOrderResponse(order);
    }

}
