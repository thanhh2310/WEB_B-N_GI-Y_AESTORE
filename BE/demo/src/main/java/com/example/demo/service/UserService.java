/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.respository.RoleRepository;
import com.example.demo.respository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserResponse createUser(UserRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Optional<Role> userRole = roleRepository.findByName("USER");

        HashSet<Role> roles = new HashSet<>();
        roles.add(userRole.get()); // Thêm role vào set
        user.setRoles(roles);  // Gán roles cho user

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);

    }

    public void deleteUser(Integer userId) {
        User user =userRepository.findById(userId).orElseThrow(()->new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        user.setActive(false);
        userRepository.save(user);
    }

    public List<User> getAll() {

        return userRepository.findByActiveTrue();
    }

    public UserResponse getUser(Integer id) {
        User user =userRepository.findById(id).orElseThrow(()->new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }
//    public UserResponse update(Integer id,UserRequest request){
//        
//    }

}
