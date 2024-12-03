package com.example.demo.service;

import com.example.demo.dto.request.ColorRequest;
import com.example.demo.dto.response.ColorResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.ColorMapper;
import com.example.demo.model.Color;
import com.example.demo.respository.ColorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;  // Repository để tương tác với cơ sở dữ liệu

    @Autowired
    private ColorMapper colorMapper;  // Mapper để chuyển đổi giữa Entity và DTO

    // Tạo mới Color
    public ColorResponse create(ColorRequest colorRequest) {

        Color color = colorMapper.toColor(colorRequest);  // Chuyển ColorRequest sang Color (Entity)
        color = colorRepository.save(color);  // Lưu vào cơ sở dữ liệu
        return colorMapper.toColorResponse(color);  // Chuyển đổi Color (Entity) sang ColorResponse
    }

    // Lấy tất cả các màu sắc
    public List<Color> getAllColors() {

        return colorRepository.findByActiveTrue();

    }

    // Lấy Color theo ID
    public ColorResponse getColorById(Integer id) {
        Color color = colorRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.COLOR_NOT_FOUND));
        if (!color.isActive()) {
            throw new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return colorMapper.toColorResponse(color);
    }

    // Cập nhật Color
    public ColorResponse update(Integer id, ColorRequest colorRequest) {
        Color color = colorRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.COLOR_NOT_FOUND));
        if (!color.isActive()) {
            throw new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND);
        }
        color.setName(colorRequest.getName());  // Cập nhật tên màu sắc
        color = colorRepository.save(color);  // Lưu vào cơ sở dữ liệu
        return colorMapper.toColorResponse(color);  // Chuyển đổi sang ColorResponse

    }

    // Xóa Color
    public void delete(Integer id) {
        Color color = colorRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.COLOR_NOT_FOUND));
        if (!color.isActive()) {
            throw new WebErrorConfig(ErrorCode.CATEGORY_NOT_FOUND);
        }
        color.setActive(false);
        colorRepository.save(color);
    }

    public void moveOn(Integer id) {
        Color color = colorRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.COLOR_NOT_FOUND));

        color.setActive(true);
        colorRepository.save(color);
    }
     public List<Color> getAllColorsForAdmin() {

        return colorRepository.findAll();

    }

}
