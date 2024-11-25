package com.example.demo.service;

import com.example.demo.dto.request.ColorRequest;
import com.example.demo.dto.response.ColorResponse;
import com.example.demo.mapper.ColorMapper;
import com.example.demo.model.Color;
import com.example.demo.respository.ColorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
    public List<ColorResponse> getAllColors() {
        List<Color> colors = colorRepository.findAll();  // Lấy tất cả các Color từ cơ sở dữ liệu
        return colors.stream()
                .map(colorMapper::toColorResponse)  // Chuyển đổi mỗi Color sang ColorResponse
                .collect(Collectors.toList());
    }

    // Lấy Color theo ID
    public ColorResponse getColorById(Integer id) {
        Optional<Color> colorOpt = colorRepository.findById(id);  // Tìm màu sắc theo ID
        return colorOpt.map(colorMapper::toColorResponse).orElse(null);  // Trả về ColorResponse nếu tìm thấy
    }

    // Cập nhật Color
    public ColorResponse update(Integer id, ColorRequest colorRequest) {
        Optional<Color> colorOpt = colorRepository.findById(id);  // Tìm màu sắc theo ID
        if (colorOpt.isPresent()) {
            Color color = colorOpt.get();
            color.setName(colorRequest.getName());  // Cập nhật tên màu sắc
            color = colorRepository.save(color);  // Lưu vào cơ sở dữ liệu
            return colorMapper.toColorResponse(color);  // Chuyển đổi sang ColorResponse
        } else {
            return null;  // Nếu không tìm thấy Color
        }
    }

    // Xóa Color
    public boolean delete(Integer id) {
        Optional<Color> colorOpt = colorRepository.findById(id);  // Tìm màu sắc theo ID
        if (colorOpt.isPresent()) {
            colorRepository.deleteById(id);  // Xóa Color
            return true;
        } else {
            return false;
        }
    }
}
