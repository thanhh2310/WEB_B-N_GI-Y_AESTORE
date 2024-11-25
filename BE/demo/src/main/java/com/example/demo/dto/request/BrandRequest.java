/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Builder
@Data
public class BrandRequest {

    private String name;

    private String description;
}
