package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import java.util.Date;
import java.util.Set;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "Orders")
@EqualsAndHashCode(exclude = {"user", "orderDetail"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "UserID")
    @JsonBackReference
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
    @JsonManagedReference
    private Set<OrderDetail> orderDetail;
    private String address;
}
