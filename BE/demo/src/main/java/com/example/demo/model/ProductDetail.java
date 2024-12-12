package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "ProductDetail")
@EqualsAndHashCode(exclude = {"images"}) 
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

    @OneToMany(mappedBy = "productDetail")
    private Set<Image> images;

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
