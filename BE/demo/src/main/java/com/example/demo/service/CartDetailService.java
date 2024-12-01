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
import com.example.demo.respository.ProductDetailRepository;
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
     private final ProductDetailRepository productDetailRepository;
    private final CartService cartService;

    public void addItemToCart(Integer cartId, Integer productDetailId, int quantity) {

        Cart cart = cartService.getCart(cartId);
        ProductDetailResponse productDetailResponse = productDetailService.getProductDetailById(productDetailId);
        ProductDetail productDetail=productDetailRepository.findById(productDetailResponse.getId()).orElseThrow(()->new WebErrorConfig(ErrorCode.PRODUCTDETAIL_NOT_FOUND));
        CartDetail cartDetail = cart.getItems()
                .stream()
                .filter(item -> item.getProductDetail().getId().equals(productDetail))
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
