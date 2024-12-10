/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.request;

import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
@Builder
public class CouponRequest {

    private BigDecimal value;

    private Date dateCreate;

    private Date dateExpire;

    private Integer quantity;

    private String name;

    private String code;
    private double minPrice;
    private double maxpPrice;
}
