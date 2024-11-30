/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.service.CartDetailService;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartdetails")
@RequiredArgsConstructor
public class CartDetailController {

    private final CartDetailService cartDetailService;
    private final CartService cartService;

    // Thêm item vào giỏ hàng
    @PostMapping("/item/add")
    public ApiResponse addItemToCart(@RequestParam(required = false) Integer cartId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {
        try {
            // Nếu cartId không được cung cấp, khởi tạo cart mới
            if (cartId == null) {
                cartId = cartService.initializeNewCart();
            }
            // Thêm item vào giỏ hàng
            cartDetailService.addItemToCart(cartId, productId, quantity);

            // Trả về phản hồi thành công
            return ApiResponse.<Void>builder()
                    .message("Add Item Success")
                    .build();

        } catch (WebErrorConfig e) {
            // Nếu gặp lỗi không tìm thấy tài nguyên, trả về thông báo lỗi
            return ApiResponse.<Void>builder()
                    .message(e.getMessage())
                    .build();
        }
    }

    // Xóa item khỏi giỏ hàng
    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ApiResponse removeItemFromCart(@PathVariable Integer cartId, @PathVariable Integer itemId) {
        try {
            // Xóa item khỏi giỏ hàng
            cartDetailService.removeItemFromCart(cartId, itemId);

            // Trả về phản hồi thành công
            return ApiResponse.<Void>builder()
                    .message("Remove Item Success")
                    .build();

        } catch (WebErrorConfig e) {
            // Nếu gặp lỗi không tìm thấy tài nguyên, trả về thông báo lỗi
            return ApiResponse.<Void>builder()
                    .message(e.getMessage())
                    .build();
        }
    }

    // Cập nhật số lượng item trong giỏ hàng
    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ApiResponse updateItemQuantity(@PathVariable Integer cartId,
            @PathVariable Integer itemId,
            @RequestParam Integer quantity) {
        try {
            // Cập nhật số lượng item trong giỏ hàng
            cartDetailService.updateItemQuantity(cartId, itemId, quantity);

            // Trả về phản hồi thành công
            return ApiResponse.<Void>builder()
                    .message("Update Item Success")
                    .build();

        } catch (WebErrorConfig e) {
            // Nếu gặp lỗi không tìm thấy tài nguyên, trả về thông báo lỗi
            return ApiResponse.<Void>builder()
                    .message(e.getMessage())
                    .build();
        }
    }
}
