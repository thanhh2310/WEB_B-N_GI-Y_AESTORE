/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.request;

import com.example.demo.model.Color;
import com.example.demo.model.Image;
import com.example.demo.model.Product;
import com.example.demo.model.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
@Builder
public class ProductDetailRequest {

    private Integer productId;  // Truyền trực tiếp đối tượng Product
    private Color color;      // Truyền trực tiếp đối tượng Color
    private Size size;        // Truyền trực tiếp đối tượng Size
    private Image image;      // Truyền trực tiếp đối tượng Image
    private BigDecimal price; // Giá sản phẩm
    private String description; // Mô tả chi tiết
    private Integer quantity;
}
