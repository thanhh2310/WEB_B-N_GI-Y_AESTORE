package com.example.demo.controller;

import com.example.demo.dto.request.ColorRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ColorResponse;
import com.example.demo.model.Color;
import com.example.demo.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colors")  // Định nghĩa endpoint cho Color
public class ColorController {

    @Autowired
    private ColorService colorService;

    // API để tạo mới Color
    @PostMapping
    public ApiResponse<ColorResponse> create(@RequestBody ColorRequest colorRequest) {
        ColorResponse colorResponse = colorService.create(colorRequest);  // Gọi service để tạo mới Color
        return ApiResponse.<ColorResponse>builder()
                .code(200)
                .message("Color created successfully")
                .result(colorResponse)
                .build();  // Trả về ApiResponse với thông tin màu sắc đã tạo
    }

    // API để lấy tất cả Color
    @GetMapping
    public ApiResponse<List<Color>> getAll() {
        List<Color> colors = colorService.getAllColors();  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<Color>>builder()
                .code(200)
                .message("All colors fetched successfully")
                .result(colors)
                .build();  // Trả về ApiResponse với danh sách tất cả các màu sắc
    }

    // API để lấy Color theo ID
    @GetMapping("/{id}")
    public ApiResponse<ColorResponse> getById(@PathVariable Integer id) {
        ColorResponse colorResponse = colorService.getColorById(id);  // Gọi service để lấy Color theo ID
        return ApiResponse.<ColorResponse>builder()
                .code(200)
                .message("Color fetched successfully")
                .result(colorResponse)
                .build();  // Trả về ApiResponse với thông tin màu sắc theo ID
    }

    // API để cập nhật Color
    @PatchMapping("/{id}")
    public ApiResponse<ColorResponse> update(@PathVariable Integer id, @RequestBody ColorRequest colorRequest) {
        ColorResponse updatedColor = colorService.update(id, colorRequest);  // Gọi service để cập nhật Color
        return ApiResponse.<ColorResponse>builder()
                .code(200)
                .message("Color updated successfully")
                .result(updatedColor)
                .build();  // Trả về ApiResponse với màu sắc đã cập nhật
    }

    // API để xóa Color
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        colorService.delete(id);  // Gọi service để xóa Color

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa thành  công ")
                .build();  // Trả về ApiResponse xác nhận đã xóa thành công hay không
    }

    @PostMapping("/moveon/{id}")
    public ApiResponse<Void> moveOn(@PathVariable Integer id) {
        colorService.moveOn(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Color move on successfully")
                .build();  // Trả
    }

    @GetMapping("/admin")
    public ApiResponse<List<Color>> getAllForAdmin() {
        List<Color> colors = colorService.getAllColorsForAdmin();  // Gọi service để lấy tất cả Color
        return ApiResponse.<List<Color>>builder()
                .code(200)
                .message("All colors fetched successfully")
                .result(colors)
                .build();  // Trả về ApiResponse với danh sách tất cả các màu sắc
    }

    @GetMapping("/name")
    public ApiResponse<Color> getAllForName(@RequestParam String name) {
        Color color = colorService.getColorByName(name);

        return ApiResponse.<Color>builder()
                .code(200)
                .message("thanh cong")
                .result(color)
                .build();

    }

    @GetMapping("/{name}")
    public ApiResponse<Color> getByName(@RequestParam String name) {
        Color color = colorService.getColorByName(name);  // Gọi service để lấy Color theo ID
        return ApiResponse.<Color>builder()
                .code(200)
                .message("Color fetched successfully")
                .result(color)
                .build();  // Trả về ApiResponse với thông tin màu sắc theo ID
    }
}
