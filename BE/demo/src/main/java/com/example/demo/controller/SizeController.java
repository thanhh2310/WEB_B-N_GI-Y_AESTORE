package com.example.demo.controller;

import com.example.demo.dto.request.SizeRequest;
import com.example.demo.dto.response.SizeResponse;
import com.example.demo.model.Size;
import com.example.demo.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sizes")  // Định nghĩa endpoint cho Size
public class SizeController {

    @Autowired
    private SizeService sizeService;

    // API để tạo mới Size
    @PostMapping
    public SizeResponse create(@RequestBody SizeRequest sizeRequest) {
        return sizeService.create(sizeRequest);  // Gọi service để tạo mới Size
    }

    // API để lấy tất cả Size
    @GetMapping
    public List<Size> getAll() {
        return sizeService.getAllSizes();  // Gọi service để lấy tất cả Size
    }

    // API để lấy Size theo ID
    @GetMapping("/{id}")
    public SizeResponse getById(@PathVariable Integer id) {
        return sizeService.getSizeById(id);  // Gọi service để lấy Size theo ID
    }

    // API để cập nhật Size
    @PutMapping("/{id}")
    public SizeResponse update(@PathVariable Integer id, @RequestBody SizeRequest sizeRequest) {
        return sizeService.update(id, sizeRequest);  // Gọi service để cập nhật Size
    }

    // API để xóa Size
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return sizeService.delete(id);  // Gọi service để xóa Size
    }
}
