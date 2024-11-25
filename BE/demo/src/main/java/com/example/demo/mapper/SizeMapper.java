package com.example.demo.mapper;

import com.example.demo.dto.request.SizeRequest;
import com.example.demo.dto.response.SizeResponse;
import com.example.demo.model.Size;
import org.springframework.stereotype.Component;

@Component
public class SizeMapper {

    // Chuyển từ SizeRequest sang Size (Entity)
    public Size toSize(SizeRequest sizeRequest) {
        Size size = new Size();
        size.setName(sizeRequest.getName());
        return size;
    }

    // Chuyển từ Size (Entity) sang SizeResponse
    public SizeResponse toSizeResponse(Size size) {
        return new SizeResponse( size.getName());
    }
}
