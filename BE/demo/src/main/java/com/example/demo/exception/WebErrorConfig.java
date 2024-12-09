/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.exception;


import lombok.Getter;

/**
 *
 * @author Admin
 */
@Getter
public class WebErrorConfig extends RuntimeException{
    ErrorCode errorCode;

    public WebErrorConfig(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    
    
}
