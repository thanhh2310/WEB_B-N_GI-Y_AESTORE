package com.example.demo.dto.response;

import com.example.demo.model.Brand;
import com.example.demo.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Integer id;
    private String name;
    private Category category;
    private Brand brand;
    private String description;
    private BigDecimal minPrice;
    private Set<String> imageUrl;
    private boolean active;
}
