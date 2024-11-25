package com.example.demo.service;

import com.example.demo.dto.request.BrandRequest;
import com.example.demo.dto.response.BrandResponse;  // Thay CategoryResponse bằng BrandResponse
import com.example.demo.mapper.BrandMapper;
import com.example.demo.model.Brand;
import com.example.demo.respository.BrandRepository;
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
    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brandMapper.toBrandResponses(brands);  // Chuyển đổi danh sách Brand sang danh sách BrandResponse
    }

    // Đọc Brand theo ID
    public BrandResponse getBrandById(Integer id) {
        Optional<Brand> brandOpt = brandRepository.findById(id);
        if (brandOpt.isPresent()) {
            return brandMapper.toBrandResponse(brandOpt.get());
        } else {
            return null;
        }
    }

    // Cập nhật Brand theo ID
    public BrandResponse update(Integer id, BrandRequest brandRequest) {
        Optional<Brand> brandOpt = brandRepository.findById(id);
        
            Brand brand = brandOpt.get();
            brand.setName(brandRequest.getName());  // Cập nhật tên của Brand
            brand.setDescription(brandRequest.getDescription());
            brandRepository.save(brand);  // Lưu thay đổi vào cơ sở dữ liệu
            return brandMapper.toBrandResponse(brand);
       
    }

    // Xóa Brand theo ID
    public boolean delete(Integer id) {
        Optional<Brand> brandOpt = brandRepository.findById(id);
        if (brandOpt.isPresent()) {
            brandRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
