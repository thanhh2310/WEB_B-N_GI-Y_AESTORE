/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import jakarta.mail.MessagingException;
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
@RequiredArgsConstructor
@RequestMapping("/Email")
public class EmailController {

    private final MailService mailService;

    @PostMapping("/sendEmail")
    public ApiResponse<String> sendEmail(@RequestParam String toEmail,@RequestParam String token) throws MessagingException{
        return ApiResponse.<String>builder()
                .result(mailService.sendEmail(toEmail,token))
                .build();
    }
   
}
