package com.example.demo.service;

import com.example.demo.dto.request.SizeRequest;
import com.example.demo.dto.response.SizeResponse;
import com.example.demo.mapper.SizeMapper;
import com.example.demo.model.Size;

import com.example.demo.respository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;  // Repository để tương tác với cơ sở dữ liệu

    @Autowired
    private SizeMapper sizeMapper;  // Mapper để chuyển đổi giữa Entity và DTO

    // Tạo mới Size
    public SizeResponse create(SizeRequest sizeRequest) {
        Size size = sizeMapper.toSize(sizeRequest);  // Chuyển SizeRequest sang Size (Entity)
        size = sizeRepository.save(size);  // Lưu vào cơ sở dữ liệu
        return sizeMapper.toSizeResponse(size);  // Chuyển đổi Size (Entity) sang SizeResponse
    }

    // Lấy tất cả các kích thước
    public List<SizeResponse> getAllSizes() {
        List<Size> sizes = sizeRepository.findAll();  // Lấy tất cả các Size từ cơ sở dữ liệu
        return sizes.stream()
                .map(sizeMapper::toSizeResponse) // Chuyển đổi mỗi Size sang SizeResponse
                .collect(Collectors.toList());
    }

    // Lấy Size theo ID
    public SizeResponse getSizeById(Integer id) {
        Optional<Size> sizeOpt = sizeRepository.findById(id);  // Tìm kích thước theo ID
        return sizeOpt.map(sizeMapper::toSizeResponse).orElse(null);  // Trả về SizeResponse nếu tìm thấy
    }

    // Cập nhật Size
    public SizeResponse update(Integer id, SizeRequest sizeRequest) {
        Optional<Size> sizeOpt = sizeRepository.findById(id);  // Tìm kích thước theo ID
        if (sizeOpt.isPresent()) {
            Size size = sizeOpt.get();
            size.setName(sizeRequest.getName());  // Cập nhật tên kích thước
            size = sizeRepository.save(size);  // Lưu vào cơ sở dữ liệu
            return sizeMapper.toSizeResponse(size);  // Chuyển đổi sang SizeResponse
        } else {
            return null;  // Nếu không tìm thấy Size
        }
    }

    // Xóa Size
    public boolean delete(Integer id) {
        Optional<Size> sizeOpt = sizeRepository.findById(id);  // Tìm kích thước theo ID
        if (sizeOpt.isPresent()) {
            sizeRepository.deleteById(id);  // Xóa Size
            return true;
        } else {
            return false;
        }
    }
}
