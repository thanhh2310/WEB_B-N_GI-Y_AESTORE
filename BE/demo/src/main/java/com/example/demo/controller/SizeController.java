package com.example.demo.controller;

import com.example.demo.dto.request.SizeRequest;
import com.example.demo.dto.response.ApiResponse;
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
    public ApiResponse<SizeResponse> create(@RequestBody SizeRequest sizeRequest) {
        SizeResponse sizeResponse = sizeService.create(sizeRequest);  // Gọi service để tạo mới Size
        return ApiResponse.<SizeResponse>builder()
                .code(200)
                .message("Size created successfully")
                .result(sizeResponse)
                .build();  // Trả về ApiResponse với thông tin size mới tạo
    }

    // API để lấy tất cả Size
    @GetMapping
    public ApiResponse<List<Size>> getAll() {
        List<Size> sizes = sizeService.getAllSizes();  // Gọi service để lấy tất cả Size
        return ApiResponse.<List<Size>>builder()
                .code(200)
                .message("All sizes fetched successfully")
                .result(sizes)
                .build();  // Trả về ApiResponse với danh sách tất cả các kích thước
    }

    // API để lấy Size theo ID
    @GetMapping("/{id}")
    public ApiResponse<SizeResponse> getById(@PathVariable Integer id) {
        SizeResponse sizeResponse = sizeService.getSizeById(id);  // Gọi service để lấy Size theo ID
        return ApiResponse.<SizeResponse>builder()
                .code(200)
                .message("Size fetched successfully")
                .result(sizeResponse)
                .build();  // Trả về ApiResponse với thông tin kích thước theo ID
    }

    // API để cập nhật Size
    @PatchMapping("/{id}")
    public ApiResponse<SizeResponse> update(@PathVariable Integer id, @RequestBody SizeRequest sizeRequest) {
        SizeResponse updatedSize = sizeService.update(id, sizeRequest);  // Gọi service để cập nhật Size
        return ApiResponse.<SizeResponse>builder()
                .code(200)
                .message("Size updated successfully")
                .result(updatedSize)
                .build();  // Trả về ApiResponse với kích thước đã cập nhật
    }

    // API để xóa Size
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
 
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Đã xóa thành công")
         
                .build();  // Trả về ApiResponse xác nhận đã xóa thành công hay không
    }
}
