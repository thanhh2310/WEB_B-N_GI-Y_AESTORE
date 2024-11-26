/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.respository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse create(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        productRepository.save(product);
        return productMapper.toResponse(product);
    }

    public ProductResponse getById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(productMapper::toResponse).orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
    }

    // READ: Get all products
    public List<Product> getAll() {
       return productRepository.findAll();
        
    }

    // UPDATE: Update an existing product
    @Transactional
    public ProductResponse update(Integer id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        productMapper.updateProductFromRequest(request, product);
        product = productRepository.save(product); // Save the updated product
        return productMapper.toResponse(product); // Return the updated response
    }

    @Transactional
    public void delete(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }

}
