/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

/**
 *
 * @author Admin
 */
import com.example.demo.dto.request.ProductDetailRequest;
import com.example.demo.dto.request.ProductDetailUpdate;
import com.example.demo.dto.response.ProductDetailResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.ProductDetailMapper;
import com.example.demo.model.Color;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import com.example.demo.model.Size;
import com.example.demo.respository.ColorRepository;
import com.example.demo.respository.ProductDetailRepository;

import java.awt.*;
import java.util.List;

import com.example.demo.respository.ProductRepository;
import com.example.demo.respository.SizeRepository;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final ProductDetailMapper productDetailMapper;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductRepository productRepository;

    public ProductDetailResponse getProductDetailByColorAndSize(Integer productId, Integer idColor, Integer idSize) {
    Color color = colorRepository.findById(idColor).orElseThrow(() -> new WebErrorConfig(ErrorCode.COLOR_NOT_FOUND));
    Size size = sizeRepository.findById(idSize)
            .orElseThrow(() -> new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));
    Product product = productRepository.findById(productId).orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));

    // Truy vấn và lấy danh sách các ProductDetail
    List<ProductDetail> productDetails = productDetailRepository
            .findByProductAndColorAndSize(product, color, size);



    ProductDetail productDetail = productDetails.isEmpty() ? null : productDetails.get(0);
    if (productDetail == null) {
        throw new WebErrorConfig(ErrorCode.PRODUCTDETAIL_NOT_FOUND); // Không tìm thấy đối tượng
    }

    return productDetailMapper.toProductDetailResponse(productDetail);
}


    public ProductDetailResponse updateProductDetail(Integer id, ProductDetailUpdate request) {
        // Tìm sản phẩm chi tiết
        ProductDetail existingProductDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));

        // Cập nhật trường Image nếu có
        if (request.getImage() != null) {
            existingProductDetail.setImage(request.getImage());
        }

        // Cập nhật trường Price nếu có
        if (request.getPrice() != null) {
            existingProductDetail.setPrice(request.getPrice());
        }

        // Cập nhật trường Description nếu có
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            existingProductDetail.setDescription(request.getDescription());
        }

        // Cập nhật trường Quantity nếu có
        if (request.getQuantity() != null) {
            existingProductDetail.setQuantity(request.getQuantity());
        }

        // Lưu lại bản cập nhật vào cơ sở dữ liệu
        existingProductDetail = productDetailRepository.save(existingProductDetail);

        // Chuyển đổi và trả về kết quả
        return productDetailMapper.toProductDetailResponse(existingProductDetail);
    }

    
    public List<ProductDetailResponse> getAll() {
        List<ProductDetailResponse> productDetailResponses = new ArrayList<>();
        for (ProductDetail p : productDetailRepository.findByActiveTrue()) {
            productDetailResponses.add(productDetailMapper.toProductDetailResponse(p));
        }
        return productDetailResponses;
    }
    public List<ProductDetailResponse> getAllForAdmin() {
        List<ProductDetailResponse> productDetailResponses = new ArrayList<>();
        for (ProductDetail p : productDetailRepository.findAll()) {
            productDetailResponses.add(productDetailMapper.toProductDetailResponse(p));
        }
        return productDetailResponses;
    }

    public void deleteProductDetail(Integer id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        productDetail.setActive(false);
        productDetailRepository.save(productDetail);
    }

    public ProductDetailResponse getProductDetailById(Integer id) {
        // Tìm kiếm ProductDetail theo id
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCTDETAIL_NOT_FOUND));
        
        // Chuyển đổi ProductDetail sang ProductDetailResponse và trả về
        return productDetailMapper.toProductDetailResponse(productDetail);
    }

    public List<ProductDetailResponse> getAllProductDetailByProduct(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        return product.getProductDetails().stream()
                .map(productDetailMapper::toProductDetailResponse) // Ánh xạ ProductDetail -> ProductDetailResponse
                .collect(Collectors.toList());  // Thu thập thành danh sách
    }

    public ProductDetailResponse create(ProductDetailRequest request) {
        // Tìm sản phẩm, màu sắc, và kích thước từ các ID
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        Size size = sizeRepository.findSizeByName(request.getSize()).orElseThrow(() -> new WebErrorConfig(ErrorCode.SIZE_NOT_FOUND));
        Color color = colorRepository.findColorByName(request.getColor()).orElseThrow(() -> new WebErrorConfig(ErrorCode.COLOR_NOT_FOUND));

        // Tạo mới ProductDetail và thiết lập các thuộc tính
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setColor(color);
        productDetail.setSize(size);
        productDetail.setImage(request.getImage());
        productDetail.setPrice(request.getPrice());
        productDetail.setDescription(request.getDescription());
        productDetail.setQuantity(request.getQuantity());
        productDetail.setActive(true);

        // Lưu vào database
        productDetail = productDetailRepository.save(productDetail);

        // Trả về ProductDetailResponse đã được chuyển đổi
        return productDetailMapper.toProductDetailResponse(productDetail);
    }

    public List<Size> getProductDetailsByColor(Integer productId, Integer colorId) {
        List<ProductDetail> details = productDetailRepository.findByProductIdAndColorIdOrderBySizeId(productId, colorId);
        List<Size> sizes=new ArrayList<>();
        for(ProductDetail p:details){
            sizes.add(p.getSize());
        }
        return sizes;
    }
     public List<Color> getProductDetailsBySize(Integer productId, Integer colorId) {
        List<ProductDetail> details = productDetailRepository.findByProductIdAndColorIdOrderBySizeId(productId, colorId);
        List<Color> colors=new ArrayList<>();
        for(ProductDetail p:details){
            colors.add(p.getColor());
        }
        return colors;
    }

    
    public void moveOn(Integer id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        productDetail.setActive(true);
        productDetailRepository.save(productDetail);
    }

    public Map<String, List<ProductDetailResponse>> getProductVariantsByColor(Integer productId) {
        List<ProductDetail> details = productDetailRepository.findByProductIdOrderByColorId(productId);
        return details.stream()
                .map(productDetailMapper::toProductDetailResponse)
                .collect(Collectors.groupingBy(
                        detail -> detail.getColor(),
                        Collectors.toList()
                ));
    }
}
