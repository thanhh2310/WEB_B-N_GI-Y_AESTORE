package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.model.Cart;
import com.example.demo.service.CartDetailService;
import com.example.demo.service.CartService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartDetailService cartDetailService;
    private final CartService cartService;

    // Thêm item vào giỏ hàng
    
    @GetMapping("/{cartId}/my-cart")
    public ApiResponse<Cart> getCart( @PathVariable Integer cartId) {
       
            Cart cart = cartService.getCart(cartId);
            return ApiResponse.<Cart>builder()
                    .message("Giỏ hàng của")
                    .result(cart)
                    .build();
         
    }

      @DeleteMapping("/{cartId}/clear")
    public ApiResponse<Void> clearCart( @PathVariable Integer cartId) {
        
            cartService.clearCart(cartId);
            return ApiResponse.<Void>builder()
                    .message("Mua hang thanh cong")
                    .build();
    }
    @GetMapping("/{cartId}/total-price")
    public ApiResponse<BigDecimal> getTotalAmount( @PathVariable Integer cartId) {
        
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
           return ApiResponse.<BigDecimal>builder()
                   .result(totalPrice)
                   .build();
       
        }
}
