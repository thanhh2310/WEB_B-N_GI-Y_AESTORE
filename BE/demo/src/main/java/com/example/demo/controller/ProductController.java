package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
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
    public List<Product> getAllProducts() {
        // Lấy danh sách tên sản phẩm từ service (hoặc danh sách bạn cần)
         return  productService.getAll();

        
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(@PathVariable Integer id) {
        return ApiResponse.<Product>builder()
                .build();

    }

    // Cập nhật sản phẩm
    @PutMapping("/{id}")
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
}
