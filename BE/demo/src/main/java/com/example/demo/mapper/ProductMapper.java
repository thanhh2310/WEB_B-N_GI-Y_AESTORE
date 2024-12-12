/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.mapper;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.respository.BrandRepository;
import com.example.demo.respository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component

public class ProductMapper {

   

    // Chuyển đổi từ ProductRequest sang Product (Entity)
    public Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());

        product.setCategory(productRequest.getCategory());
        product.setBrand(productRequest.getBrand());

        return product;
    }

    // Chuyển đổi từ Product (Entity) sang ProductResponse
    public ProductResponse toResponse(Product product) {
        return  ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .category(product.getCategory())
                .active(product.isActive())
                .build();
    }
    public void updateProductFromRequest(ProductRequest request, Product product) {
        // Cập nhật các trường trong product từ request
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        // Nếu có category trong request, cập nhật category của sản phẩm
        if (request.getCategory() != null) {
            product.setCategory(request.getCategory());
        }

        // Nếu có brand trong request, cập nhật brand của sản phẩm
        if (request.getBrand() != null) {
            product.setBrand(request.getBrand());
        }
    }

    
}
