/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.dto.request.CouponRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.model.Coupon;
import com.example.demo.service.CouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/Coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @PostMapping
    ApiResponse<Coupon> create(@RequestBody CouponRequest request){
        return ApiResponse.<Coupon>builder()
                .result(couponService.create(request))
                .build();
    }
    @GetMapping
    public ApiResponse<List<Coupon>> getAll() {
        // Sử dụng phương thức đúng để lấy coupon đang hoạt động và sắp xếp theo giá trị giảm dần
        List<Coupon> activeCoupons = couponService.getActiveCouponsSortedByPercentageDesc();
        return ApiResponse.<List<Coupon>>builder()
                .result(activeCoupons)
                .build();
    }
     @GetMapping("/Admin")
    public ApiResponse<List<Coupon>> getAllForAdmin() {
        // Sử dụng phương thức đúng để lấy coupon đang hoạt động và sắp xếp theo giá trị giảm dần
        List<Coupon> activeCoupons = couponService.getAllCoupons();
        return ApiResponse.<List<Coupon>>builder()
                .result(activeCoupons)
                .build();
    }
   @PutMapping
    public ApiResponse<Coupon> update(@PathVariable Integer id, @RequestBody CouponRequest request) {
        Coupon updatedCoupon = couponService.update(id, request);
        return ApiResponse.<Coupon>builder()
                .result(updatedCoupon)
                .build();
    }

    // Xóa coupon
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        couponService.delete(id);
        return ApiResponse.<Void>builder()
                .result(null)
                .build();
    }
}
