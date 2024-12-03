package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Tạo mới sản phẩm
    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        // Gọi service để tạo sản phẩm
        ProductResponse createdProduct = productService.create(productRequest);

        // Tạo phản hồi ApiResponse
        return ApiResponse.<ProductResponse>builder()
                .code(1000)
                .message("Product created successfully")
                .result(createdProduct)
                .build();
    }

    // Lấy tất cả sản phẩm
    @GetMapping

    public ApiResponse<List<ProductResponse>> getAll() {
        List<ProductResponse> products = productService.getAll();  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<ProductResponse>>builder()
                .code(200)
                .message("All product fetched successfully")
                .result(products)
                .build();  // Trả về ApiResponse với danh sách tất cả các sản phẩm
    }
    @GetMapping("/admin")
    public ApiResponse<List<ProductResponse>> getAllForAdmin() {
        List<ProductResponse> products = productService.getAllForAdmin();  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<ProductResponse>>builder()
                .code(200)
                .message("All product fetched successfully")
                .result(products)
                .build();  // Trả về ApiResponse với danh sách tất cả các sản phẩm
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Integer id) {
        ProductResponse product = productService.getById(id);
        return ApiResponse.<ProductResponse>builder()
                .result(product)
                .message("Long an cut")
                .build();

    }

    // Cập nhật sản phẩm
    @PatchMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productRequest) {
        // Gọi service để cập nhật sản phẩm
        ProductResponse updatedProduct = productService.update(id, productRequest);
        return ApiResponse.<ProductResponse>builder()
                .result(updatedProduct)
                .message("xoa duoc roi")
                .build();

    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Xoa roi day")
                .build();
    }

    @PostMapping("/moveon/{id}")
    public ApiResponse<Void> moveOn(@PathVariable Integer id) {
        productService.moveOn(id);
        return ApiResponse.<Void>builder()
                .message("move on succes")
                .build();
    }

    @GetMapping("/productdetails/{id}")
    public ApiResponse<List<ProductDetailResponse>> getAllProductDetail(@PathVariable Integer id) {
        List<ProductDetailResponse> products = productService.getAllProductDetailResponses(id);  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<ProductDetailResponse>>builder()
                .code(200)
                .message("All product fetched successfully")
                .result(products)
                .build();  // Trả về ApiResponse với danh sách tất cả các sản phẩm
    }
    @GetMapping("/name/{name}")
    public ApiResponse<List<ProductResponse>> getAllProductByName(@PathVariable String name) { 
        List<ProductResponse> products = productService.getProductByName(name);  // Gọi service để lấy tất cả Color
        
        return ApiResponse.<List<ProductResponse>>builder()
                .code(200)
                .message("All product fetched successfully")
                .result(products)
                .build();  // Trả về ApiResponse với danh sách tất cả các sản phẩm
    }
    @GetMapping("/test")
    public ApiResponse<List<Product>> getAllProductByName1(@RequestBody String name) {
        List<Product> products = productService.getProductByName1(name);  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<Product>>builder()
                .code(200)
                .message("All product fetched successfully")
                .result(products)
                .build();  // Trả về ApiResponse với danh sách tất cả các sản phẩm
    }
    
    

}
