package com.example.demo.mapper;

import com.example.demo.dto.request.ColorRequest;
import com.example.demo.dto.response.ColorResponse;
import com.example.demo.model.Color;
import org.springframework.stereotype.Component;

@Component
public class ColorMapper {

    // Chuyển từ ColorRequest sang Color (Entity)
    public Color toColor(ColorRequest colorRequest) {
        Color color = new Color();
        color.setName(colorRequest.getName());
        return color;
    }

    // Chuyển từ Color (Entity) sang ColorResponse
    public ColorResponse toColorResponse(Color color) {
        return new ColorResponse( color.getName());
    }
}
