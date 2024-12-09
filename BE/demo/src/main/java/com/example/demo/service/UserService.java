/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Cart;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.respository.CartRepository;
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
    private final CartRepository cartRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    // Tạo người dùng mới
    public UserResponse createUser(UserRequest request) {
        // Tạo đối tượng User từ request
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu

        // Tìm role USER
        Optional<Role> userRole = roleRepository.findByName("USER");
        HashSet<Role> roles = new HashSet<>();
        roles.add(userRole.get()); // Thêm role vào set
        user.setRoles(roles);  // Gán roles cho user

        // Lưu User vào cơ sở dữ liệu trước
        user = userRepository.save(user);

        // Tạo đối tượng Cart và liên kết với User đã lưu
        Cart cart = new Cart();
        cart.setUser(user);  // Gán user vào cart
        cartRepository.save(cart);  // Lưu Cart vào cơ sở dữ liệu

        // Cập nhật lại User với Cart nếu cần thiết
        user.setCart(cart);  // Liên kết Cart với User
        user = userRepository.save(user);  // Cập nhật lại User với Cart

        // Trả về thông tin User
        return userMapper.toUserResponse(user);
    }

    // Xóa người dùng (xóa mềm)
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

        if (!user.isActive()) {
            throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);  // Nếu người dùng đã bị xóa mềm
        }

        user.setActive(false);  // Đánh dấu người dùng là không hoạt động
        userRepository.save(user);  // Lưu thay đổi
    }

    // Kích hoạt lại người dùng
    public void moveOn(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        if(user.isActive()){
            user.setActive(false);
        }else {
            user.setActive(true);
        }
          // Kích hoạt người dùng
        userRepository.save(user);  // Lưu thay đổi
    }

    // Lấy tất cả người dùng đang hoạt động
    public List<User> getAll() {
        return userRepository.findByActiveTrue();  // Lấy các user đang hoạt động
    }

    // Lấy thông tin người dùng theo ID
    public UserResponse getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        if (!user.isActive()) {
            throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserResponse(user);
    }

    // Cập nhật thông tin người dùng
    public UserResponse update(Integer id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra nếu người dùng đã bị xóa mềm
        if (!user.isActive()) {
            throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);  // Nếu người dùng không còn hoạt động
        }

        // Cập nhật thông tin người dùng từ request
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());  // Cập nhật tên người dùng nếu có
        }
        if (request.getDob() != null) {
            user.setDob(request.getDob());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        // Lưu lại thông tin người dùng đã cập nhật
        user = userRepository.save(user);

        // Trả về thông tin người dùng đã cập nhật
        return userMapper.toUserResponse(user);
    }
}
