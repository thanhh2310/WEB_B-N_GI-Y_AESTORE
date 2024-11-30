/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.request;

import java.math.BigDecimal;
import java.util.Set;

/**
 *
 * @author Admin
 */
public class CartRequest {
    private Integer cartId;
    private Set<CartDetailRequest> items;
    private BigDecimal totalAmount;
}
