/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
@Builder
public class OrderDetailResponse {

    private Integer id;

    private Integer orderId;

    private ProductDetailResponse productDetail;

    private BigDecimal price;

    private Integer quantity;

    private String attention;
}
