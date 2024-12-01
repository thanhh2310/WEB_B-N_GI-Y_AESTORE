/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
      @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody  UserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<User>> getAll() {
        List<User> colors = userService.getAll();  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<User>>builder()
                .code(200)
                .message("All user fetched successfully")
                .result(colors)
                .build();  // Trả về ApiResponse với danh sách tất cả các màu sắc
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Integer userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/my-info")
//    ApiResponse<UserResponse> getMyInfo() {
//        return ApiResponse.<UserResponse>builder()
//                .result(userService.getMyInfo())
//                .build();
//    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable Integer userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("Succes update")
                .result(userService.update(userId, request))
                .build();
    }
      @PostMapping("/moveon/{userId}")
    ApiResponse<Void> moveOn(@PathVariable Integer userId) {
        userService.moveOn(userId);
        return ApiResponse.<Void>builder()
                .message("Succes update")
                
                .build();
    }
}
