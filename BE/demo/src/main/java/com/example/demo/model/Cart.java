package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "DateCreate")
    private Date dateCreate;

    @Column(name = "LastUpdate")
    private Date lastUpdate;

    private Integer cartItems;

    private BigDecimal total;
} 