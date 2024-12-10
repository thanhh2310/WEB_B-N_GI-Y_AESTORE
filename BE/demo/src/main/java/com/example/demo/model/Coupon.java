package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "Coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(name = "DateCreate")
    private Date dateCreate;

    @Column(name = "DateExpire", nullable = false)
    private Date dateExpire;
  
    private Integer quantity;

    private String name;
    private double minPrice;
    private double maxPrice;

    @Column(unique = true)
    private String code;
    private boolean active=true;
     public boolean isExpired() {
        // So sánh ngày hết hạn với ngày hiện tại
        if (dateExpire.before(new Date())) {
            // Nếu coupon đã hết hạn, cập nhật active thành false
            this.active = false;
            return true;
        }
        return false;
    }



} 