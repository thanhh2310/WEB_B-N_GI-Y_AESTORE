package com.example.demo.controller;

import com.example.demo.dto.request.CategoryRequset;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.model.Category;
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
   public ApiResponse<List<Category>> getAll() {
        List<Category> colors = categoryService.getAllCategories();  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<Category>>builder()
                .code(200)
                .message("All cate fetched successfully")
                .result(colors)
                .build();  // Trả về ApiResponse với danh sách tất cả các màu sắc
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Integer id) {
       CategoryResponse categoryResponse=categoryService.getCategoryById(id);
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryResponse)
                .message("Chính là nó")
                
                .build();
       
    }
     @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequset categoryRequset) {
        CategoryResponse updatedCategory = categoryService.update(id, categoryRequset);
        return ApiResponse.<CategoryResponse>builder()
                .code(200)
                .message("Category updated successfully")
                .result(updatedCategory)
                .build();
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Category deleted successfully")
                .result("Category with ID " + id + " has been deleted.")
                .build();
    }
}
    
    

