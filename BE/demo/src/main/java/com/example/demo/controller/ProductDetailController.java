package com.example.demo.controller;

import com.example.demo.dto.request.ProductDetailRequest;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.model.ProductDetail;
import com.example.demo.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productdetails")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @PostMapping
    public ApiResponse<ProductDetailResponse> createProduct(@RequestBody ProductDetailRequest productRequest) {
        // Gọi service để tạo sản phẩm
        ProductDetailResponse createdProduct = productDetailService.createProductDetail(productRequest);

        // Tạo phản hồi ApiResponse
        return ApiResponse.<ProductDetailResponse>builder()
                .code(1000)
                .message("Product created successfully")
                .result(createdProduct)
                .build();
    }

}
