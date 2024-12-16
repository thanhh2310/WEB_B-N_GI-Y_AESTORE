/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.mapper;

import com.example.demo.dto.response.OrderDetailResponse;
import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
@RequiredArgsConstructor
public class OrderDetailMapper {
    private  final ProductDetailMapper productDetailMapper;
   public  OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .productDetail(productDetailMapper.toProductDetailResponse(orderDetail.getProductDetail()))
                .build();
    }
}
