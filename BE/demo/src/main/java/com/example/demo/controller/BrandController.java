package com.example.demo.controller;

import com.example.demo.dto.request.BrandRequest;
import com.example.demo.dto.response.BrandResponse;
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
    public ResponseEntity<BrandResponse> createBrand(@RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandService.create(brandRequest);
        return new ResponseEntity<>(brandResponse, HttpStatus.CREATED);
    }

    // Lấy danh sách tất cả các Brand
    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        List<BrandResponse> brandResponses = brandService.getAllBrands();
        return new ResponseEntity<>(brandResponses, HttpStatus.OK);
    }

    // Lấy Brand theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Integer id) {
        BrandResponse brandResponse = brandService.getBrandById(id);
        if (brandResponse != null) {
            return new ResponseEntity<>(brandResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Cập nhật Brand theo ID
    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Integer id, @RequestBody BrandRequest brandRequest) {
        BrandResponse updatedBrand = brandService.update(id, brandRequest);
        if (updatedBrand != null) {
            return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Xóa Brand theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Integer id) {
        boolean isDeleted = brandService.delete(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Trả về HTTP 204 nếu xóa thành công
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Trả về HTTP 404 nếu không tìm thấy
        }
    }
}
