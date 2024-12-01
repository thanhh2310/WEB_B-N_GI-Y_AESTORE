package com.example.demo.mapper;

import com.example.demo.dto.request.CategoryRequset;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.model.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;

import org.springframework.stereotype.Component;
@NoArgsConstructor
@Component
public class CategoryMapper {
    
     public Category toCategory(CategoryRequset categoryRequest){
        Category category=new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setActive(categoryRequest.getActive() != null ? categoryRequest.getActive() : true);
        return category;
        
    }
    
    public CategoryResponse toCategoryResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.isActive())
                .build();
    }
   public List<CategoryResponse> toCategoryResponses(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryResponse)  // Chuyển mỗi Category sang CategoryResponse
                .collect(Collectors.toList());  // Thu thập kết quả vào danh sách
    }
   
}
