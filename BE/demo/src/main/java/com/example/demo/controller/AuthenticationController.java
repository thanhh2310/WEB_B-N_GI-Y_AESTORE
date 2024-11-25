/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;


import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.TokenRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.TokenResponse;
import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticatedService;
    @PostMapping("/log-in")
    public  ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticateRequest){
        var result=authenticatedService.authenticate(authenticateRequest);
        return  ApiResponse.<AuthenticationResponse>builder()
                .result(result)                    
                .build();
    }
    @PostMapping("/check")
     public  ApiResponse<TokenResponse> veryfier(@RequestBody TokenRequest request) throws JOSEException, ParseException{
        var result=authenticatedService.veryfier(request);
        return  ApiResponse.<TokenResponse>builder()
                .result(result)                    
                .build();
    }
}
