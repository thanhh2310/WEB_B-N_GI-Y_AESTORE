/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.request;

import com.example.demo.model.ProductDetail;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
@Builder

public class CartDetailRequest {

    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDetail product;
}
