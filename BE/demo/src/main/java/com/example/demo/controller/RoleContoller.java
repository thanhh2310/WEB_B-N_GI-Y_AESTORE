/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleContoller {
    private final RoleService roleService;
        @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
 public ApiResponse<List<Role>> getAll() {
        List<Role> colors = roleService.getAll();  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<Role>>builder()
                .code(200)
                .message("All colors fetched successfully")
                .result(colors)
                .build();  // Trả về ApiResponse với danh sách tất cả các màu sắc
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String name) {
        roleService.deleteByName(name);
        return ApiResponse.<Void>builder().build();
    }
}
