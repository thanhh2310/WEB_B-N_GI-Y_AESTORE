package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @ManyToMany
    @JoinTable(
        name = "QuantityCoupon",
        joinColumns = @JoinColumn(name = "OrderDetailID"),
        inverseJoinColumns = @JoinColumn(name = "CouponID")
    )
    private Set<Coupon> coupons;

    private BigDecimal total;

    @Column(nullable = false)
    private Integer quantity;

    private String attention;
} 