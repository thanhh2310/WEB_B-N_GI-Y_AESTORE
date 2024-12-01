/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.response;

import com.example.demo.model.ProductDetail;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
@Builder
public class CartDetailResponse {

    private Integer id;                  // ID của CartDetail
    private Integer productDetailId; // Toàn bộ đối tượng ProductDetail
    private Integer quantity;

}
