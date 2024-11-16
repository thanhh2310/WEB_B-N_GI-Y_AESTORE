package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

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

    @Column(unique = true)
    private String code;
} 