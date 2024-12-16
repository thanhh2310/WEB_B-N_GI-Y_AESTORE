package com.example.demo.mapper;

import com.example.demo.dto.request.ProductDetailRequest;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Color;
import com.example.demo.model.Image;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.model.Size;
import com.example.demo.respository.ColorRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.respository.SizeRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
        // Lấy thông tin Color, Size và Product từ các repository
        Size size = sizeRepository.findSizeByName(request.getSize())
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));
        Color color = colorRepository.findColorByName(request.getColor())
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.COLOR_NOT_FOUND));  // Lỗi sửa từ SIZE_NOT_FOUND thành COLOR_NOT_FOUND
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));

        // Tạo đối tượng ProductDetail và gán các thuộc tính
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setColor(color);
        productDetail.setSize(size);
        productDetail.setPrice(request.getPrice());
        productDetail.setDescription(request.getDescription());
        productDetail.setQuantity(request.getQuantity());

        // Tạo các đối tượng Image từ Set<String> URL ảnh
        Set<Image> images = new HashSet<>();
        for (String imageUrl : request.getImage()) {
            Image image = new Image();
            image.setUrl(imageUrl);  // Gán URL cho ảnh
            images.add(image);  // Thêm ảnh vào Set
        }

        // Gán Set<Image> vào ProductDetail
        productDetail.setImages(images);

        return productDetail;
    }

    // Chuyển đổi từ ProductDetail (Entity) sang ProductDetailResponse
    public ProductDetailResponse toProductDetailResponse(ProductDetail productDetail) {
        // Tạo Set để lưu trữ các URL của hình ảnh
        Set<String> imageUrls = new HashSet<>();

        // Dùng vòng lặp for để duyệt qua tất cả các đối tượng Image và lấy URL
        for (Image image : productDetail.getImages()) {
            imageUrls.add(image.getUrl());  // Thêm URL của mỗi hình ảnh vào Set
        }

        // Trả về ProductDetailResponse với tất cả các thuộc tính
        return ProductDetailResponse.builder()
                .Id(productDetail.getId()) // Thêm ID của ProductDetail
                .productId(productDetail.getProduct().getId()) // Lấy productId của sản phẩm
                .color(productDetail.getColor().getName()) // Lấy tên của Color
                .size(productDetail.getSize().getName()) // Lấy tên của Size
                .image(imageUrls) // Truyền Set các URL hình ảnh
                .price(productDetail.getPrice()) // Giá sản phẩm
                .description(productDetail.getDescription()) // Mô tả chi tiết sản phẩm
                .quantity(productDetail.getQuantity())
                .active(productDetail.isActive())// Số lượng sản phẩm
                .build();
    }

}
