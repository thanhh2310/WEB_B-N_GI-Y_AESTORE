/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.mapper;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
@Data
@NoArgsConstructor
public class UserMapper {

    public User toUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        
        user.setDob(userRequest.getDob());
        user.setGender(userRequest.getGender());
        user.setFullName(userRequest.getFullName());

        return user;

    }

    public UserResponse toUserResponse(User user) {

        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .status(user.getStatus())
                .dob(user.getDob())
                .gender(user.getGender())
                .fullName(user.getFullName())
                .build();

        return userResponse;
    }
}
