package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "OrderDetail")

public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ProductDetailID")
    private ProductDetail productDetail;

    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    private String attention;
     public OrderDetail(Order order, ProductDetail productDetail, Integer quantity, BigDecimal price) {
        this.order = order;
        this.productDetail = productDetail;
        this.quantity = quantity;
        this.price = price;
    }
}
