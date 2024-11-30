package com.example.demo.service;

import com.example.demo.dto.request.CartDetailRequest;
import com.example.demo.dto.response.CartDetailResponse;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;

import com.example.demo.model.Cart;
import com.example.demo.model.CartDetail;
import com.example.demo.model.ProductDetail;

import com.example.demo.respository.CartDetailRepository;
import com.example.demo.respository.CartRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartDetailService {

    private final CartDetailRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductDetailService productDetailService;
    private final CartService cartService;

    public void addItemToCart(Integer cartId, Integer productId, int quantity) {
        //1. Get the cart
        //2. Get the product
        //3. Check if the product already in the cart
        //4. If Yes, then increase the quantity with the requested quantity
        //5. If No, then initiate a new CartItem entry.
        Cart cart = cartService.getCart(cartId);
        ProductDetail productDetail = productDetailService.getProductDetailById(productId);
        CartDetail cartDetail = cart.getItems()
                .stream()
                .filter(item -> item.getProductDetail().getId().equals(productId))
                .findFirst()
                .orElse(new CartDetail());

        if (cartDetail.getId() == null) {
            cartDetail.setCart(cart);
            cartDetail.setProductDetail(productDetail);
            cartDetail.setQuantity(quantity);
            cartDetail.setUnitPrice(productDetail.getPrice());
        } else {
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        }
        cartDetail.setTotalPrice();
        cart.addItem(cartDetail);
        cartItemRepository.save(cartDetail);
        cartRepository.save(cart);
    }

    public void removeItemFromCart(Integer cartId, Integer productId) {
        Cart cart = cartService.getCart(cartId);
        CartDetail itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    public void updateItemQuantity(Integer cartId, Integer productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProductDetail().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProductDetail().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartDetail::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    public CartDetail getCartItem(Integer cartId, Integer productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProductDetail().getId().equals(productId))
                .findFirst().orElseThrow(() -> new WebErrorConfig(ErrorCode.CARTITEM_NOT_FOUND));
    }
}
