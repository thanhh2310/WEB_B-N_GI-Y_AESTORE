package com.example.demo.dto.request;

import lombok.Builder;
import lombok.Data;
import com.example.demo.model.*;

/**
 *
 * @author Admin
 */
@Builder
@Data
public class ProductRequest {
    private String name;
    private String description;
    private Category category;
    private Brand brand;
}
