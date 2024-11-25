package com.example.demo.mapper;

import com.example.demo.dto.request.BrandRequest;
import com.example.demo.dto.response.BrandResponse;

import com.example.demo.model.Brand;
import com.example.demo.model.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
@NoArgsConstructor
@Component
public class BrandMapper {
    
     public Brand toBrand(BrandRequest brandRequset){
        Brand brand=new Brand();
        brand.setName(brandRequset.getName());
        brand.setDescription(brandRequset.getDescription());
        return brand;
        
    }
    
    public BrandResponse toBrandResponse(Brand brand){
        return BrandResponse.builder()
                .name(brand.getName())
                .description(brand.getDescription())
                .build();
    }
   public List<BrandResponse> toBrandResponses(List<Brand> brands) {
        return brands.stream()
                .map(this::toBrandResponse)  // Chuyển mỗi Category sang CategoryResponse
                .collect(Collectors.toList());  // Thu thập kết quả vào danh sách
    }
   
}
