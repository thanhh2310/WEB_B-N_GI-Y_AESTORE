/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.request;

import com.example.demo.model.Coupon;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data

public class OrderRequest {

   private Integer userId;
   private String address;
   private String paymentMethod;

}
