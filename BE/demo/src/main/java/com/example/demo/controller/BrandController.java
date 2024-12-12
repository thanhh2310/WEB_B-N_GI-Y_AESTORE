package com.example.demo.controller;

import com.example.demo.dto.request.BrandRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.BrandResponse;
import com.example.demo.model.Brand;
import com.example.demo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    // Tạo mới Brand
    @PostMapping
    public ApiResponse<BrandResponse> createBrand(@RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandService.create(brandRequest);
        return ApiResponse.<BrandResponse>builder()
                .result(brandResponse)
                .build();
    }

    // Lấy danh sách tất cả các Brand
    @GetMapping
public ApiResponse<List<Brand>> getAll() {
        List<Brand> colors = brandService.getAllBrands();  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<Brand>>builder()
                .code(200)
                .message("All brands fetched successfully")
                .result(colors)
                .build();  // Trả về ApiResponse với danh sách tất cả các màu sắc
    }

    // Lấy Brand theo ID
    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getBrandById(@PathVariable Integer id) {
        BrandResponse brandResponse = brandService.getBrandById(id);
       return ApiResponse.<BrandResponse>builder()
                .result(brandResponse)
                .build();
    }

    // Cập nhật Brand theo ID
    @PatchMapping("/{id}")
    public ApiResponse<BrandResponse> updateBrand(@PathVariable Integer id, @RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandService.update(id, brandRequest);
        return ApiResponse.<BrandResponse>builder()
                .result(brandResponse)
                .build();
    }

    // Xóa Brand theo ID
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBrand(@PathVariable Integer id) {
        brandService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Da xoa thanh cong")
                .build();
    }
    @PostMapping("/moveon/{id}")
    public ApiResponse<Void> moveOn(@PathVariable Integer id) {
        brandService.moveOn(id);
        return ApiResponse.<Void>builder()
                .message("moveon")
                .build();
    }
    @GetMapping("/admin")
    public ApiResponse<List<Brand>> getAllForAdmin() {
        List<Brand> brands = brandService.getAllBrandsForAdmin();  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<Brand>>builder()
                .code(200)
                .message("All brands fetched successfully")
                .result(brands)
                .build();  // Trả về ApiResponse với danh sách tất cả các màu sắc
    }
     @GetMapping("/name")
    public ApiResponse<BrandResponse> getBrandByName(@RequestParam String name) {
        BrandResponse brandResponse = brandService.getBrandByName(name);
       return ApiResponse.<BrandResponse>builder()
                .result(brandResponse)
                .build();
    }
}
