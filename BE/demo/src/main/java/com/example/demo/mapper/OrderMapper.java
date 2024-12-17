package com.example.demo.mapper;

import com.example.demo.dto.response.OrderDetailResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.model.Order;
import com.example.demo.model.Coupon;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderDetailMapper orderDetailMapper;

    public OrderResponse tOrderResponse(Order order) {
        // Kiểm tra null cho user và các trường khác nếu cần
        User user = order.getUser();
        Set<Coupon> coupons = order.getCoupons();

        // Tạo một set để chứa các OrderDetailResponse
        Set<OrderDetailResponse> orderDetails = new HashSet<>();

        // Duyệt qua các OrderDetail trong đơn hàng và chuyển đổi chúng thành OrderDetailResponse
        for (OrderDetail orderDetail : order.getOrderDetail()) {
            OrderDetailResponse orderDetailResponse = orderDetailMapper.toOrderDetailResponse(orderDetail);
            orderDetails.add(orderDetailResponse);
        }

        return OrderResponse.builder()
                .id(order.getId())
                .userId(user.getUserId()) // Kiểm tra userId của user
                .orderDetail(orderDetails)
                .status(order.getStatus())
                .total(order.getTotal())
                .coupons(coupons) // Ánh xạ coupons
                .address(order.getAddress()) // Ánh xạ address
                .dateCreate(order.getDateCreate())
                .build();
    }
}
