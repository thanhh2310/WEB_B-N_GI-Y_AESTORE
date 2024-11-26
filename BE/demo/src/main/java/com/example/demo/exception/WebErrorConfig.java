/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.exception;



/**
 *
 * @author Admin
 */
public class WebErrorConfig extends RuntimeException{
    ErrorCode errorCode;

    public WebErrorConfig(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    
    
}
