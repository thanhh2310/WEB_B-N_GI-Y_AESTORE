package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Table(name = "CartDetail")
@Builder
@NoArgsConstructor  // Thêm constructor mặc định
@AllArgsConstructor // Constructor có tham số
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "productDetail_id")
    private ProductDetail productDetail;

   
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // Phương thức tính tổng giá trị
    public void setTotalPrice() {
        if (this.unitPrice != null) {
            this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
        } else {
            this.totalPrice = BigDecimal.ZERO;  // Nếu unitPrice là null, thì totalPrice là 0
        }
    }

    // Khởi tạo đối tượng CartDetail với các tham số
    public CartDetail(int quantity, BigDecimal unitPrice, ProductDetail productDetail, Cart cart) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productDetail = productDetail;
        this.cart = cart;
        setTotalPrice();  // Tính toán giá trị tổng sau khi khởi tạo
    }
}
