    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package com.example.demo.service;

    import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductDetailResponse;
    import com.example.demo.dto.response.ProductResponse;
    import com.example.demo.exception.ErrorCode;
    import com.example.demo.exception.WebErrorConfig;
    import com.example.demo.mapper.ProductMapper;
    import com.example.demo.model.Product;
    import com.example.demo.model.ProductDetail;
    import com.example.demo.respository.ProductDetailRepository;
    import com.example.demo.respository.ProductRepository;
    import jakarta.transaction.Transactional;
    import java.math.BigDecimal;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;

    /**
     *
     * @author Admin
     */
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class ProductService {

        private final ProductRepository productRepository;
        private final ProductMapper productMapper;
        private final ProductDetailRepository productDetailRepository;

        public ProductResponse create(ProductRequest request) {
            Product product = productMapper.toProduct(request);
            productRepository.save(product);
            return productMapper.toResponse(product);
        }

        public ProductResponse getById(Integer id) {
            Optional<Product> product = productRepository.findById(id);
            return product.map(productMapper::toResponse).orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
        }

        // READ: Get all products
        public List<ProductResponse> getAll() {
            log.info("Fetching all active products with their minimum price and image.");

            // Lấy tất cả các sản phẩm còn hoạt động từ repository
            List<Product> activeProducts = productRepository.findByActiveTrue();

            // Danh sách để lưu kết quả trả về
            List<ProductResponse> productResponses = new ArrayList<>();

            // Duyệt qua tất cả các sản phẩm
            for (Product product : activeProducts) {
                // Tìm chi tiết sản phẩm có giá thấp nhất cho mỗi sản phẩm
                ProductDetail productDetail = productDetailRepository.findMinPriceDetailByProductId(product.getId());

                // Tạo đối tượng ProductResponse và thiết lập dữ liệu
                ProductResponse productResponse = productMapper.toResponse(product);

                // Nếu có chi tiết sản phẩm thì set giá và hình ảnh
                if (productDetail != null) {
                    // Thiết lập giá cho sản phẩm
                    productResponse.setMinPrice(productDetail.getPrice());
                    // Thiết lập URL ảnh cho sản phẩm
                    productResponse.setImageUrl(productDetail.getImage().getUrl());
                }

                // Thêm sản phẩm vào danh sách kết quả
                productResponses.add(productResponse);
            }

            // Trả về danh sách các sản phẩm
            return productResponses;
        }

        // UPDATE: Update an existing product
        @Transactional
        public ProductResponse update(Integer id, ProductRequest request) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));
            productMapper.updateProductFromRequest(request, product);
            product = productRepository.save(product); // Save the updated product
            return productMapper.toResponse(product); // Return the updated response
        }

        @Transactional
        public void delete(Integer id) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new WebErrorConfig(ErrorCode.PRODUCT_NOT_FOUND));

            product.setActive(false);
            productRepository.save(product);
        }
       
    }
