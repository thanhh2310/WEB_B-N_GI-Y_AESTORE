package com.example.security.Exception;

public enum ErrorCode {
    
    USER_NOT_FOUND(1001, "User not found"),
    USER_EXISTED(1002, "user existed"),
    
    WRONG_PASSWORD(1003, "Mat khau khong chinh xac ");
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
