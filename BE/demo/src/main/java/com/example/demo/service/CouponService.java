package com.example.demo.service;

import com.example.demo.dto.request.CouponRequest;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.CouponMapper;
import com.example.demo.model.Coupon;
import com.example.demo.respository.CouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    public Coupon create(CouponRequest request) {
        return couponRepository.save(couponMapper.toCoupon(request));

    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // Read: Lấy coupon theo ID
    public Coupon getCouponById(Integer id) {
        return couponRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.COUPON_NOT_FOUND));
    }

    // Update: Cập nhật coupon
    public Coupon update(Integer id, CouponRequest request) {
        Coupon existingCoupon = couponRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.COUPON_NOT_FOUND));

        // Cập nhật thông tin coupon
        existingCoupon.setValue(request.getValue());
        existingCoupon.setDateExpire(request.getDateExpire());
        existingCoupon.setQuantity(request.getQuantity());
        existingCoupon.setName(request.getName());
        existingCoupon.setCode(request.getCode());
        // Kiểm tra xem coupon có hết hạn không và cập nhật active nếu cần
        existingCoupon.isExpired();
        // Cập nhật vào cơ sở dữ liệu
        return couponRepository.save(existingCoupon);
    }

    // Delete: Xóa coupon
    public void delete(Integer id) {
          Coupon existingCoupon = couponRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.COUPON_NOT_FOUND));
          existingCoupon.setActive(false);
          couponRepository.save(existingCoupon);
    }
     public List<Coupon> getActiveCouponsSortedByPercentageDesc() {
        return couponRepository.findByActiveTrue(Sort.by(Sort.Order.desc("value")));
    }
     
}
