/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.respository;

import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Admin
 */
@Repository

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

    List<ProductDetail> findAllByProduct(Product product);

    @Query(value = "SELECT * FROM product_detail pd WHERE pd.productid = :productId AND pd.price = (SELECT MIN(pd2.price) FROM product_detail pd2 WHERE pd2.productid = :productId)", nativeQuery = true)
    ProductDetail findMinPriceDetailByProductId(@Param("productId") Integer productId);
    List<ProductDetail>findByActiveTrue();
    
    List<ProductDetail> findByProductIn(List<Product> products);

}
