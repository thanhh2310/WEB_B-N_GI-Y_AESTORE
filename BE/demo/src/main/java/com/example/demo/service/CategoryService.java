/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.dto.request.CategoryRequset;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.model.Category;
import com.example.demo.respository.CategoryRepository;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@NoArgsConstructor
@AllArgsConstructor
public class CategoryService {
    @Autowired
    private   CategoryRepository categoryRespository;
   @Autowired
    private   CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryRequset categoryRequset) {
       Category category =categoryMapper.toCategory(categoryRequset);
        categoryRespository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    // Read All
    public List<Category> getAllCategories() {
        return categoryRespository.findByActiveTrue();
       
    }

    public List<Category> getAllIncludeInactive() {
        return categoryRespository.findAllByActiveTrueOrActiveFalse();
    }

    // Read By ID
    public CategoryResponse getCategoryById(Integer id) {
        Category categoryOpt = categoryRespository.findById(id).orElseThrow(
                ()->new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        
            return categoryMapper.toCategoryResponse(categoryOpt);
        
    }

    // Update
    public CategoryResponse update(Integer id, CategoryRequset categoryRequest) {
        Category category = categoryRespository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND));

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        
        // Cập nhật trạng thái active nếu được cung cấp
        if (categoryRequest.getActive() != null) {
            category.setActive(categoryRequest.getActive());
        }
        
        categoryRespository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    // Delete
    public void delete(Integer id) {
       Category category = categoryRespository.findById(id).orElseThrow(
                ()->new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        category.setActive(false);
        categoryRespository.save(category);
    }
}
