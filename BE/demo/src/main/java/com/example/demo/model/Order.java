package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "`Order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "DateCreate")
    private Date dateCreate;

    private String status;
    private BigDecimal total;
    @ManyToMany
    @JoinTable(
            name = "QuantityCoupon",
            joinColumns = @JoinColumn(name = "OrderDetailID"),
            inverseJoinColumns = @JoinColumn(name = "CouponID")
    )
    private Set<Coupon> coupons;
        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetail;

}
