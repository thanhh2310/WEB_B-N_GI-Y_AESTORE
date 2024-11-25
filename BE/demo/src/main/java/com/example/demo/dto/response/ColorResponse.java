package com.example.demo.dto.response;

import lombok.Data;

@Data
public class ColorResponse {

    private String name;  // Tên của màu sắc

    public ColorResponse( String name) {

        this.name = name;
    }
}
