/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.dto.request.CategoryRequset;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.model.Category;
import com.example.demo.respository.CategoryRespository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
    private   CategoryRespository categoryRespository;
   @Autowired
    private   CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryRequset categoryRequset) {
       Category category =categoryMapper.toCategory(categoryRequset);
        categoryRespository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    // Read All
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRespository.findAll();
        return categoryMapper.toCategoryResponses(categories);
    }

    // Read By ID
    public CategoryResponse getCategoryById(Integer id) {
        Optional<Category> categoryOpt = categoryRespository.findById(id);
        if (categoryOpt.isPresent()) {
            return categoryMapper.toCategoryResponse(categoryOpt.get());
        } else {
            return null;
        }
    }

    // Update
    public CategoryResponse update(Integer id, CategoryRequset categoryRequset) {
        Optional<Category> categoryOpt = categoryRespository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setName(categoryRequset.getName());
            categoryRespository.save(category);
            return categoryMapper.toCategoryResponse(category);
        } else {
            return null;
        }
    }

    // Delete
    public boolean delete(Integer id) {
        Optional<Category> categoryOpt = categoryRespository.findById(id);
        if (categoryOpt.isPresent()) {
            categoryRespository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
