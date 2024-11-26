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
        return categoryRespository.findAll();
       
    }

    // Read By ID
    public CategoryResponse getCategoryById(Integer id) {
        Category categoryOpt = categoryRespository.findById(id).orElseThrow(
                ()->new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        
            return categoryMapper.toCategoryResponse(categoryOpt);
        
    }

    // Update
    public CategoryResponse update(Integer id, CategoryRequset categoryRequset) {
       Category category = categoryRespository.findById(id).orElseThrow(
                ()->new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        
         
            category.setName(categoryRequset.getName());
            categoryRespository.save(category);
            return categoryMapper.toCategoryResponse(category);
        
    }

    // Delete
    public void delete(Integer id) {
       Category category = categoryRespository.findById(id).orElseThrow(
                ()->new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        categoryRespository.delete(category);
    }
}
