package com.example.demo.mapper;

import com.example.demo.dto.request.ProductDetailRequest;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Color;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.model.Size;
import com.example.demo.respository.ColorRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.respository.SizeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class ProductDetailMapper {
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    // Chuyển đổi từ ProductDetailRequest sang ProductDetail (Entity)
    public ProductDetail toProductDetail(ProductDetailRequest request) {
         Size size =sizeRepository.findSizeByName(request.getColor()).orElseThrow(()->new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));
        Color color=colorRepository.findColorByName(request.getColor()).orElseThrow(()->new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));
       Product product=productRepository.findById(request.getProductId()).get();
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setColor(color);
        productDetail.setSize(size);
        productDetail.setImage(request.getImage());
        productDetail.setPrice(request.getPrice());
        productDetail.setDescription(request.getDescription());
        productDetail.setQuantity(request.getQuantity());
        return productDetail;
    }

    // Chuyển đổi từ ProductDetail (Entity) sang ProductDetailResponse
    public ProductDetailResponse toProductDetailResponse(ProductDetail productDetail) {
        return ProductDetailResponse.builder()
<<<<<<< Updated upstream
                .productId(productDetail.getId())
                .color(productDetail.getColor())
                .size(productDetail.getSize())
                .image(productDetail.getImage())
                .price(productDetail.getPrice())
                .description(productDetail.getDescription())
                .quantity(productDetail.getQuantity())
=======
                .Id(productDetail.getId())
                .productId(productDetail.getProduct().getId())  // Chỉ lấy productId
                .color(productDetail.getColor().getName())  // Truyền trực tiếp đối tượng Color
                .size(productDetail.getSize().getName())    // Truyền trực tiếp đối tượng Size
                .image(productDetail.getImage())  // Truyền trực tiếp đối tượng Image
                .price(productDetail.getPrice())  // Truyền giá sản phẩm
                .description(productDetail.getDescription())  // Truyền mô tả chi tiết
                .quantity(productDetail.getQuantity())  // Truyền số lượng sản phẩm
>>>>>>> Stashed changes
                .build();
    }
}

