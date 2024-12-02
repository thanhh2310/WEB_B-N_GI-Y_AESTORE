package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "ProductDetail")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ColorID")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "SizeID")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "ImageID")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    @Column(nullable = false)
    private Integer quantity;

    private boolean active = true;
}
