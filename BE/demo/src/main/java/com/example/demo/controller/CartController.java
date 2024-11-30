package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.service.CartDetailService;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartDetailService cartDetailService;
    private final CartService cartService;

    // Thêm item vào giỏ hàng
    @PostMapping("/item/add")
    public ApiResponse<Void> addItemToCart(@RequestParam(required = false) Integer cartId,
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
                    .message("Thêm sản phẩm vào giỏ hàng thành công")
                    .build();

        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .message("Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng")
                    .build();
        }
    }

    // Xóa item khỏi giỏ hàng
    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ApiResponse<Void> removeItemFromCart(@PathVariable Integer cartId, @PathVariable Integer itemId) {
        try {
            // Xóa item khỏi giỏ hàng
            cartDetailService.removeItemFromCart(cartId, itemId);

            // Trả về phản hồi thành công
            return ApiResponse.<Void>builder()
                    .message("Sản phẩm đã được xóa khỏi giỏ hàng")
                    .build();

        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .message("Có lỗi xảy ra khi xóa sản phẩm khỏi giỏ hàng")
                    .build();
        }
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ApiResponse<Void> updateItemQuantity(@PathVariable Integer cartId,
            @PathVariable Integer itemId,
            @RequestParam Integer quantity) {
        try {
            // Cập nhật số lượng sản phẩm trong giỏ hàng
            cartDetailService.updateItemQuantity(cartId, itemId, quantity);

            // Trả về phản hồi thành công
            return ApiResponse.<Void>builder()
                    .message("Cập nhật số lượng sản phẩm thành công")
                    .build();

        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .message("Có lỗi xảy ra khi cập nhật số lượng sản phẩm")
                    .build();
        }
    }
}
