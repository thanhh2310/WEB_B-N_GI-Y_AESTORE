package com.example.demo.dto.response;

import lombok.Data;

@Data
public class SizeResponse {
    
    private String name;  // Tên của kích thước (ví dụ: S, M, L, XL)

    public SizeResponse( String name) {
   
        this.name = name;
    }
}
