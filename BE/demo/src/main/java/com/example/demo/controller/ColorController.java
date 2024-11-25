package com.example.demo.controller;

import com.example.demo.dto.request.ColorRequest;
import com.example.demo.dto.response.ColorResponse;
import com.example.demo.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")  // Định nghĩa endpoint cho Color
public class ColorController {

    @Autowired
    private ColorService colorService;

    // API để tạo mới Color
    @PostMapping
    public ColorResponse create(@RequestBody ColorRequest colorRequest) {
        return colorService.create(colorRequest);  // Gọi service để tạo mới Color
    }

    // API để lấy tất cả Color
    @GetMapping
    public List<ColorResponse> getAll() {
        return colorService.getAllColors();  // Gọi service để lấy tất cả Color
    }

    // API để lấy Color theo ID
    @GetMapping("/{id}")
    public ColorResponse getById(@PathVariable Integer id) {
        return colorService.getColorById(id);  // Gọi service để lấy Color theo ID
    }

    // API để cập nhật Color
    @PutMapping("/{id}")
    public ColorResponse update(@PathVariable Integer id, @RequestBody ColorRequest colorRequest) {
        return colorService.update(id, colorRequest);  // Gọi service để cập nhật Color
    }

    // API để xóa Color
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return colorService.delete(id);  // Gọi service để xóa Color
    }
}
