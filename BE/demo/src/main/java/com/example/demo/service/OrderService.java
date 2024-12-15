/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.ENUMS.OrderStatus;
import com.example.demo.dto.request.OrderRequest;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Address;
import com.example.demo.model.Cart;
import com.example.demo.model.CartDetail;
import com.example.demo.model.Coupon;
import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.ProductDetail;
import com.example.demo.model.User;
import com.example.demo.respository.CouponRepository;
import com.example.demo.respository.OrderRepository;
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
    
    public Order placeOrder(OrderRequest request) {
        
        Cart cart = cartService.getCartByUserId(request.getUserId());
        log.info(cart.getId()+"Vu");
        Order order = create(cart);
        order.setAddress(request.getAddress());
        // Tạo chi tiết đơn hàng từ các sản phẩm trong giỏ hàng
        List<OrderDetail> orderItemList = createOrderDetails(order, cart);
        order.setOrderDetail(new HashSet<>(orderItemList));
        // Tính tổng giá trị đơn hàng
        BigDecimal total = getTotalPrice(orderItemList);
        order.setTotal(total);
        // Lưu đơn hàng và xóa giỏ hàng
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }
    
    private List<OrderDetail> createOrderDetails(Order order, Cart cart) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        
        for (CartDetail cartDetail : cart.getItems()) {
            ProductDetail productDetail = cartDetail.getProductDetail();
            productDetail.setQuantity(productDetail.getQuantity() - cartDetail.getQuantity());
            productDetailRepository.save(productDetail);
            
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProductDetail(productDetail);
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getUnitPrice());
            orderDetails.add(orderDetail);
        }
        
        return orderDetails;
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
}
