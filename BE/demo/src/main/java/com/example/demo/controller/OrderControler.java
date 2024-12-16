/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderControler {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> create(@RequestBody OrderRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.placeOrder(request))
                .build();
    }

    @GetMapping("/addCoupon")
    public ApiResponse<Order> addCoupon(@RequestParam Integer orderId, @RequestParam String code) {
        return ApiResponse.<Order>builder()
                .result(orderService.addCoupon(orderId, code))
                .build();
    }

    @GetMapping("/username")
    public ApiResponse getAllOrder(@RequestParam String username) {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrderByUser(username))
                .build();
    }
    // Phương thức cập nhật trạng thái đơn hàng

    @PatchMapping("/updateStatus")
    public ApiResponse<OrderResponse> updateOrderStatus(
            @RequestParam Integer orderId,
            @RequestParam String newStatus) {
        // Cập nhật trạng thái của đơn hàng và trả về phản hồi
        OrderResponse updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ApiResponse.<OrderResponse>builder()
                .result(updatedOrder)
                .build();
    }
}
