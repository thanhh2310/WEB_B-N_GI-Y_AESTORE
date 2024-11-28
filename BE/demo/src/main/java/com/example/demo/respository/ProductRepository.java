package com.example.demo.respository;

import com.example.demo.dto.response.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product>findByActiveTrue();
    

}
