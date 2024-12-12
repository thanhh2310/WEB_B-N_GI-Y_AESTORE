/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.dto.request.AddressRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.model.Address;
import com.example.demo.service.AddressService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    @PostMapping
    ApiResponse<Address> create(@RequestBody AddressRequest request){
        return ApiResponse.<Address>builder()
                .result(addressService.create(request))
                .build();
    }
    @GetMapping
    ApiResponse<List<Address>> getAll(@RequestParam Integer userId){
        return ApiResponse.<List<Address>>builder()
                .result(addressService.getAllByUser(userId))
                .build();
    }
}
