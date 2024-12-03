package com.example.demo.controller;

import com.example.demo.dto.request.ProductDetailRequest;
import com.example.demo.dto.request.ProductDetailUpdate;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.model.Color;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.model.Size;
import com.example.demo.service.ProductDetailService;
import jakarta.websocket.server.PathParam;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/product/{idProduct}/color/{idColor}/size/{idSize}")
    public ApiResponse<ProductDetailResponse> getProductDetail(
            @PathVariable Integer idProduct,
            @PathVariable Integer idColor,
            @PathVariable Integer idSize) {

        // Gọi service để lấy chi tiết sản phẩm theo màu và kích thước
        ProductDetailResponse createdProduct = productDetailService.getProductDetailByColorAndSize(idProduct, idColor, idSize);

        // Trả về phản hồi ApiResponse
        return ApiResponse.<ProductDetailResponse>builder()
                .code(1000)
                .message("Tìm thấy sản phẩm với ID: " + createdProduct.getId())
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

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        productDetailService.deleteProductDetail(id);
        return ApiResponse.<Void>builder()
                .message("đã xóa")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<ProductDetailResponse> update(@PathVariable Integer id, @RequestBody ProductDetailUpdate request) {
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
                .message("sản phẩm " + id)
                .build();

    }

    @PostMapping
    public ApiResponse<ProductDetailResponse> create(@RequestBody ProductDetailRequest request) {
        return ApiResponse.<ProductDetailResponse>builder()
                .result(productDetailService.create(request))
                .build();
    }

    // Lấy danh sách kích thước theo màu sắc
    @GetMapping("/product/{productId}/color/{colorId}")
    public ApiResponse<List<Size>> getProductDetailsByColor(
            @PathVariable Integer productId,
            @PathVariable Integer colorId
    ) {
        List<Size> details = productDetailService.getProductDetailsByColor(productId, colorId);
        return ApiResponse.<List<Size>>builder()
                .result(details)
                .message("Lấy danh sách kích thước theo màu thành công")
                .build();
    }

// Lấy danh sách màu sắc theo kích thước
    @GetMapping("/product/{productId}/size/{sizeId}")
    public ApiResponse<List<Color>> getProductDetailsBySize(
            @PathVariable Integer productId,
            @PathVariable Integer sizeId
    ) {
        List<Color> details = productDetailService.getProductDetailsBySize(productId, sizeId);
        return ApiResponse.<List<Color>>builder()
                .result(details)
                .message("Lấy danh sách màu sắc theo kích thước thành công")
                .build();
    }

    @GetMapping("/product/{productId}/variants")
    public ApiResponse<Map<String, List<ProductDetailResponse>>> getProductVariantsByColor(
            @PathVariable Integer productId
    ) {
        Map<String, List<ProductDetailResponse>> variants = productDetailService.getProductVariantsByColor(productId);
        return ApiResponse.<Map<String, List<ProductDetailResponse>>>builder()
                .result(variants)
                .message("Lấy danh sách biến thể theo nhóm màu thành công")
                .build();
    }

    @PostMapping("/moveon/{id}")
    public ApiResponse<Void> moveOn(@PathVariable Integer id) {
        productDetailService.moveOn(id);
        return ApiResponse.<Void>builder()
                .message("đã bật lại")
                .build();
    }

}
