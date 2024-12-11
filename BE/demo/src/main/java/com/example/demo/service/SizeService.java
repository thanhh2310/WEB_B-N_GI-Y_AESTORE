package com.example.demo.service;

import ch.qos.logback.core.spi.ErrorCodes;
import com.example.demo.dto.request.SizeRequest;
import com.example.demo.dto.response.SizeResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
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
    public List<Size> getAllSizesForAdmin() {
        return sizeRepository.findAll();  // Lấy tất cả các Size từ cơ sở dữ liệu

    }
    
    public List<Size> getAllSizes (){
        return sizeRepository.findByActiveTrue();  // Lấy tất cả các Size từ cơ sở dữ liệu

    }

    // Lấy Size theo ID
    public SizeResponse getSizeById(Integer id) {
        Optional<Size> sizeOpt = sizeRepository.findById(id);  // Tìm kích thước theo ID
        return sizeOpt.map(sizeMapper::toSizeResponse).orElse(null);  // Trả về SizeResponse nếu tìm thấy
    }

    // Cập nhật Size
    public SizeResponse update(Integer id, SizeRequest sizeRequest) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));  // Tìm kích thước theo ID

        size.setName(sizeRequest.getName());  // Cập nhật tên kích thước
        size = sizeRepository.save(size);  // Lưu vào cơ sở dữ liệu
        return sizeMapper.toSizeResponse(size);  // Chuyển đổi sang SizeResponse

    }

    // Xóa Size
    public void delete(Integer id) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));  // Tìm kích thước theo ID
        size.setActive(false);
        sizeRepository.save(size);
    }
    public void moveOn(Integer id) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));  // Tìm kích thước theo ID
        size.setActive(true);
        sizeRepository.save(size);
    }
     public SizeResponse getSizeByName(String name) {
        Size size= sizeRepository.findSizeByName(name).orElseThrow(()->new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));
        return sizeMapper.toSizeResponse(size);
    }
}
