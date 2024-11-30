/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.ENUMS.OrderStatus;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Cart;
import com.example.demo.model.CartDetail;
import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.ProductDetail;
import com.example.demo.respository.OrderRepository;
import com.example.demo.respository.ProductDetailRepository;
import com.example.demo.respository.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CartService cartService;

    public Order create(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("CHOXACNHAN");
        order.setDateCreate(new Date());
        return order;

    }
     public Order placeOrder(Integer userId) {
        Cart cart   = cartService.getCartByUserId(userId);
        Order order = create(cart);
        List<OrderDetail> orderItemList = createOrderDetails(order, cart);
        order.setOrderDetail(new HashSet<>(orderItemList));
        order.setTotal(getTotalPrice(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private List<OrderDetail> createOrderDetails(Order order, Cart cart) {
        List<OrderDetail> orderDetails = new ArrayList<>(); // Tạo danh sách OrderItem

        // Duyệt qua từng cartItem trong giỏ hàng
        for (CartDetail cartDetail : cart.getItems()) {
            // Lấy sản phẩm từ cartItem
            ProductDetail productDetail = cartDetail.getProductDetail();

            // Cập nhật số lượng tồn kho của sản phẩm
            productDetail.setQuantity(productDetail.getQuantity() - cartDetail.getQuantity());

            // Lưu sản phẩm với số lượng tồn kho mới
            productDetailRepository.save(productDetail);

            // Tạo OrderItem và thêm vào danh sách
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProductDetail(productDetail);
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getUnitPrice());
            orderDetails.add(orderDetail);// Thêm OrderItem vào danh sách
        }

        return orderDetails;  // Trả về danh sách OrderItem
    }

    private BigDecimal getTotalPrice(List<OrderDetail> orderDetails) {
        BigDecimal total = BigDecimal.ZERO;

        // Duyệt qua từng OrderDetail để tính tổng giá trị
        for (OrderDetail orderDetail : orderDetails) {
            // Tính giá trị của từng OrderDetail (đơn giá * số lượng)
            BigDecimal itemTotal = orderDetail.getPrice()
                    .multiply(new BigDecimal(orderDetail.getQuantity()));

            // Cộng dồn giá trị vào tổng
            total = total.add(itemTotal);
        }

        return total;  // Trả về tổng giá trị đơn hàng
    
    }
    public Order getOrder(Integer Id) {
        return orderRepository.findById(Id).orElseThrow(()->new WebErrorConfig(ErrorCode.USER_EXISTED));
        
    }

}
