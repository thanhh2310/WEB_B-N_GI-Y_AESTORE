/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Cart;
import com.example.demo.respository.CartDetailRepository;
import com.example.demo.respository.CartRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

   
    public Cart getCart(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.CART_NOT_FOUND));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }


    @Transactional
    
      public void clearCart(Integer id) {
        Cart cart = getCart(id); // Retrieve the cart
        cartDetailRepository.deleteAllByCartId(id);
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.getItems().clear(); // Remove items from the cart object
        cartRepository.deleteById(id); // Delete the cart itself
    }

   
    public BigDecimal getTotalPrice(Integer id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

 
   public Integer initializeNewCart() {
    Cart newCart = new Cart();
    return cartRepository.save(newCart).getId(); // ID được tạo tự động và trả về
}


    public Cart getCartByUserId(Integer userId) {
        return cartRepository.findByUser_UserId(userId);
    }
}
