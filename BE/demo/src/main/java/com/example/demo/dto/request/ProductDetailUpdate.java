/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.request;

import com.example.demo.model.Image;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
@Builder
public class ProductDetailUpdate {

    private Image image;      // Truyền trực tiếp đối tượng Image
    private BigDecimal price; // Giá sản phẩm
    private String description; // Mô tả chi tiết
    private Integer quantity;
}
