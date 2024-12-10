/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.mapper;

import com.example.demo.dto.request.CouponRequest;
import com.example.demo.model.Coupon;
import lombok.Builder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
@Builder
public class CouponMapper {
    public Coupon toCoupon(CouponRequest request)
    {
        Coupon coupon=new Coupon();
        coupon.setName(request.getName());
        coupon.setCode(coupon.getCode());
        coupon.setDateCreate(request.getDateCreate());
        coupon.setDateExpire(request.getDateExpire());
        coupon.setQuantity(request.getQuantity());
        coupon.setValue(request.getValue());
        coupon.setMinPrice(request.getMinPrice());
        coupon.setMaxPrice(request.getMaxpPrice());
        return coupon;
        
    }
}
