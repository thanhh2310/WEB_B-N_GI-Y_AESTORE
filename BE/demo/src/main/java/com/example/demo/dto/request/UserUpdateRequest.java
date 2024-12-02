/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto.request;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Builder
@Data
public class UserUpdateRequest {

    private String phone;

    private Date dob;

    private Character gender;

    private String fullName;
}
