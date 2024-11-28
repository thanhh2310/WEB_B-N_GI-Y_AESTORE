package com.example.demo.exception;

public enum ErrorCode {
    
    USER_NOT_FOUND(1001, "Không thấy người dùng đâu cả"),
    USER_EXISTED(1002, "Người dùng này đã tồn tại"),
    
    WRONG_PASSWORD(1003, "Mat khau khong chinh xac "),
    PRODUCT_NOT_FOUND(1004,"Không thấy sản phẩm đâu cả"),
    BRAND_NOT_FOUND(1005,"Không thấy sản brand đâu cả"),
    COLOR_NOT_FOUND(1006,"Không thấy sản brand đâu cả"),
    SIZE_NOT_FOUND(1007,"Không thấy sản Size đâu cả");
    private final int code;
    private final String message;

    // Constructor của enum để gán giá trị cho mỗi hằng số
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Các phương thức getter
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
