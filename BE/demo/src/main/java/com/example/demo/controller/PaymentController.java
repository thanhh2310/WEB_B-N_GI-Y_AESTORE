/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.PaymentResponse;
import com.example.demo.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

 
    private final PaymentService paymentService;

    @PostMapping("/createPayment")
    public PaymentResponse createPayment(HttpServletRequest req) throws UnsupportedEncodingException {
        return paymentService.createPayment( req);  // Truyền req vào đây
    }
}

