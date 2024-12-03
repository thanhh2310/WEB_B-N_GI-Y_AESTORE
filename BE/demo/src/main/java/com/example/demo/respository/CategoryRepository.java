package com.example.demo.respository;

import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Admin
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Tạo phương thức để lọc các Category có trạng thái active = true
    List<Category> findByActiveTrue();  // Tự động sinh truy vấn SELECT * FROM Category WHERE active = true
    List<Category> findAllByActiveTrueOrActiveFalse();
    Optional<Category> findByName(String name);
}
