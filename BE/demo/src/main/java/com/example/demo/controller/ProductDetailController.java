package com.example.demo.controller;

import com.example.demo.dto.request.ProductDetailRequest;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.service.ProductDetailService;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productdetails")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @PostMapping("/{idProduct}/{idColor}/{idSize}")
    public ApiResponse<ProductDetailResponse> getProductDetail(@PathVariable Integer idProduct, @PathVariable Integer idColor, @PathVariable Integer idSize) {
        // Gọi service để tạo sản phẩm
        ProductDetailResponse createdProduct = productDetailService.getProductDetailByColorAndSize(idProduct, idColor, idSize);

        // Tạo phản hồi ApiResponse
        return ApiResponse.<ProductDetailResponse>builder()
                .code(1000)
                .message("tìm được sản phẩm "+createdProduct.getId())
                .result(createdProduct)
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductDetailResponse>> getAll() {
        return ApiResponse.<List<ProductDetailResponse>>builder()
                .message("Toàn bộ sản phẩm ")
                .result(productDetailService.getAll())
                .build();
    }

    @DeleteMapping
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        productDetailService.deleteProductDetail(id);
        return ApiResponse.<Void>builder()
                .message("đã xóa")
                .build();
    }

    @PatchMapping
    public ApiResponse<ProductDetailResponse> update(@PathVariable Integer id, @RequestBody ProductDetailRequest request) {
        ProductDetailResponse productDetailResponse = productDetailService.updateProductDetail(id, request);
        return ApiResponse.<ProductDetailResponse>builder()
                .message("Cập nhật thành công")
                .result(productDetailResponse)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDetailResponse> getProductById(@PathVariable Integer id) {
        ProductDetailResponse product = productDetailService.getProductDetailById(id);
        return ApiResponse.<ProductDetailResponse>builder()
                .result(product)
                .message("Long an cut")
                .build();

    }
    @PostMapping
    public ApiResponse<ProductDetailResponse> create(@RequestBody ProductDetailRequest request){
        return ApiResponse.<ProductDetailResponse>builder()
                .result(productDetailService.create(request))
                .build();
    }

}
