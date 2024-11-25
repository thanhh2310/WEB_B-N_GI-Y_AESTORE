package com.example.demo.controller;

import com.example.demo.dto.request.CategoryRequset;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequset categoryRequset) {
        CategoryResponse categoryResponse = categoryService.create(categoryRequset);

        return ApiResponse.<CategoryResponse>builder()
                .code(200)
                .message("Category created successfully")
                .result(categoryResponse)
                .build();
    }

    // Get all categories
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse>>builder()
                .code(200)
                .message("Categories fetched successfully")
                .result(categoryResponses)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Integer id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        if (categoryResponse != null) {
            return ApiResponse.<CategoryResponse>builder()
                    .code(200)
                    .message("Category found")
                    .result(categoryResponse)
                    .build();
        } else {
            return ApiResponse.<CategoryResponse>builder()
                    .code(404)
                    .message("Category not found")
                    .result(null)
                    .build();
        }
    }
    
}
