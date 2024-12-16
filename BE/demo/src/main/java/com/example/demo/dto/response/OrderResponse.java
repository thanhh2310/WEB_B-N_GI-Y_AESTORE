/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.response;

import com.example.demo.model.Coupon;
import java.util.Date;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
@Builder

public class OrderResponse {

    private Integer id;

    private Integer userId;

    private Date dateCreate;

    private Set<Coupon> coupons;

    private Set<OrderDetailResponse> orderDetail;
    private String address;
    private String status;
}
