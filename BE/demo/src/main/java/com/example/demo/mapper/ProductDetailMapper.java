package com.example.demo.mapper;

import com.example.demo.dto.request.ProductDetailRequest;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.respository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class ProductDetailMapper {
    private final ProductRepository productRepository;
    // Chuyển đổi từ ProductDetailRequest sang ProductDetail (Entity)
    public ProductDetail toProductDetail(ProductDetailRequest request) {
       Product product=productRepository.findById(request.getProductId()).get();
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setColor(request.getColor());
        productDetail.setSize(request.getSize());
        productDetail.setImage(request.getImage());
        productDetail.setPrice(request.getPrice());
        productDetail.setDescription(request.getDescription());
        productDetail.setQuantity(request.getQuantity());
        return productDetail;
    }

    // Chuyển đổi từ ProductDetail (Entity) sang ProductDetailResponse
    public ProductDetailResponse toProductDetailResponse(ProductDetail productDetail) {
        return ProductDetailResponse.builder()
                .productId(productDetail.getId())
                .color(productDetail.getColor())
                .size(productDetail.getSize())
                .image(productDetail.getImage())
                .price(productDetail.getPrice())
                .description(productDetail.getDescription())
                .quantity(productDetail.getQuantity())
                .build();
    }
}
