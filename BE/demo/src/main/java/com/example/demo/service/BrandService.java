package com.example.demo.service;

import com.example.demo.dto.request.BrandRequest;
import com.example.demo.dto.response.BrandResponse;  // Thay CategoryResponse bằng BrandResponse
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.BrandMapper;
import com.example.demo.model.Brand;
import com.example.demo.respository.BrandRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    // Tạo mới Brand
    public BrandResponse create(BrandRequest brandRequest) {
        Brand brand = brandMapper.toBrand(brandRequest);  // Chuyển đổi từ BrandRequest sang Brand
        brandRepository.save(brand);  // Lưu Brand vào cơ sở dữ liệu
        return brandMapper.toBrandResponse(brand);  // Chuyển đổi từ Brand sang BrandResponse
    }

    // Đọc tất cả các Brand
    public List<Brand> getAllBrands() {
        return brandRepository.findByActiveTrue();
    }

    // Đọc Brand theo ID
    public BrandResponse getBrandById(Integer id) {
        Brand brand = brandRepository.findById(id).
                orElseThrow(() -> new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND));
        if (!brand.isActive()) {
            throw new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND);
        }
        return brandMapper.toBrandResponse(brand);
    }

    // Cập nhật Brand theo ID
    public BrandResponse update(Integer id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND));
        if (!brand.isActive()) {
            throw new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND);
        }
        brand.setName(brandRequest.getName());
        brand.setDescription(brandRequest.getDescription());

        // Cập nhật trạng thái active nếu được cung cấp
        if (brandRequest.getActive() != null) {
            brand.setActive(brandRequest.getActive());
        }

        brandRepository.save(brand);
        return brandMapper.toBrandResponse(brand);
    }

    // Xóa Brand theo ID
    public void delete(Integer id) {
        Brand brand = brandRepository.findById(id).
                orElseThrow(() -> new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND));
        brand.setActive(false);
        brandRepository.save(brand);

    }
    public void moveOn(Integer id){
        Brand brand = brandRepository.findById(id).
                orElseThrow(() -> new WebErrorConfig(ErrorCode.BRAND_NOT_FOUND));
        brand.setActive(true);
        brandRepository.save(brand);
    }
      public List<Brand> getAllBrandsForAdmin() {
        return brandRepository.findAll();
    }
}
