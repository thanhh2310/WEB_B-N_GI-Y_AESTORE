package com.example.demo.respository;

import com.example.demo.dto.response.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

@Repository

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByActiveTrue();

    List<Product> findByName(String name);

    @Query(value = "SELECT * FROM product WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%')) AND active = 1", nativeQuery = true)
    List<Product> findActiveProductsByName(@Param("name") String name);

}
