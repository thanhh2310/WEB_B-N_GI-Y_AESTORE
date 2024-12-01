/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

/**
 *
 * @author Admin
 */


import com.example.demo.dto.request.ProductDetailRequest;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.ProductDetailMapper;
import com.example.demo.model.Color;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.model.Size;
import com.example.demo.respository.ColorRepository;
import com.example.demo.respository.ProductDetailRepository;

import java.awt.*;
import java.util.List;

import com.example.demo.respository.ProductRepository;
import com.example.demo.respository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductDetailMapper productDetailMapper;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductRepository productRepository;

    
    public ProductDetailResponse createProductDetail(Integer productId,Integer idColor,Integer idSize ) {
        Color color =colorRepository.findById(idColor).orElseThrow(()-> new WebErrorConfig(ErrorCode.COLOR_NOT_FOUND));
        Size size=sizeRepository.findById(idColor).orElseThrow(()->new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));
        Product product= productRepository.findById(productId).orElseThrow(()->new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setColor(color);
        productDetail.setSize(size);
        return productDetailMapper.toProductDetailResponse(productDetail);
    }

     
    public ProductDetailResponse updateProductDetail(Integer id, ProductDetailRequest request) {
        ProductDetail existingProductDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        Product product= productRepository.findById(request.getProductId()).orElseThrow(()->new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        existingProductDetail.setProduct(product);
        existingProductDetail.setColor(request.getColor());
        existingProductDetail.setSize(request.getSize());
        existingProductDetail.setImage(request.getImage());
        existingProductDetail.setPrice(request.getPrice());
        existingProductDetail.setDescription(request.getDescription());
        existingProductDetail.setQuantity(request.getQuantity());

        existingProductDetail = productDetailRepository.save(existingProductDetail);
        return productDetailMapper.toProductDetailResponse(existingProductDetail);
    }
    public List<ProductDetail> getAll(){
        return productDetailRepository.findAll();
    }
    public void deleteProductDetail(Integer id){
        ProductDetail productDetail =productDetailRepository.findById(id)
                .orElseThrow(()->new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        productDetail.setActive(false);
        productDetailRepository.save(productDetail);
    }
     public ProductDetail getProductDetailById(Integer id) {
        // Tìm kiếm ProductDetail theo id
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));

        // Chuyển đổi ProductDetail sang ProductDetailResponse và trả về
        return productDetail;
    }
}
