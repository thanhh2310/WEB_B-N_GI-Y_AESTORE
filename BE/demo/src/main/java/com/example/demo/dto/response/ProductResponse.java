/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.response;

import com.example.demo.model.Color;
import com.example.demo.model.Image;
import com.example.demo.model.Product;
import com.example.demo.model.Size;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
@Builder
public class ProductResponse {

    private Color color;

    private Size size;

    private Image image;

    private Product product;

    private BigDecimal price;

    private String description;

    private Integer quantity;
}
