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
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private final AuthenticationService authenticationService;
    private final MailService emailService;

    // Tạo người dùng mới
    public UserResponse createUser(UserRequest request) throws MessagingException {
        // Tạo đối tượng User từ request
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu

        // Tìm role USER
        Optional<Role> userRole = roleRepository.findByName("USER");
        HashSet<Role> roles = new HashSet<>();
        userRole.ifPresent(roles::add); // Thêm role vào set nếu tồn tại
        user.setRoles(roles);  // Gán roles cho user
        String verificationCode = UUID.randomUUID().toString();
        user.setVerificationCode(verificationCode); // Lưu mã xác nhận vào người dùng
        // Lưu User vào cơ sở dữ liệu trước
        user = userRepository.save(user);

        // Tạo đối tượng Cart và liên kết với User đã lưu
        Cart cart = new Cart();
        cart.setUser(user);  // Gán user vào cart
        cartRepository.save(cart);  // Lưu Cart vào cơ sở dữ liệu

        // Cập nhật lại User với Cart nếu cần thiết
        user.setCart(cart);  // Liên kết Cart với User
        user = userRepository.save(user);  // Cập nhật lại User với Cart
        emailService.sendEmail(user.getEmail(), user.getVerificationCode());

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
        if (user.isActive()) {
            user.setActive(false);
        } else {
            user.setActive(true);
        }
        // Kích hoạt người dùng
        userRepository.save(user);  // Lưu thay đổi
    }

    // Lấy tất cả người dùng đang hoạt động
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();  // Lấy tất cả user
        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : users) {
            // Chuyển đổi từng User thành UserResponse và thêm vào danh sách
            userResponses.add(userMapper.toUserResponse(user));
        }

        return userResponses;
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

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    public UserResponse findUserByToken(String token) throws ParseException, JOSEException {
        String username = authenticationService.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);

    }

    public boolean verifyEmail(String verificationCode) {
        // Tìm người dùng trong cơ sở dữ liệu theo mã xác thực
        Optional<User> userOpt = userRepository.findByVerificationCode(verificationCode);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Cập nhật trạng thái xác thực của người dùng
            user.setEmailVerified(true);

            userRepository.save(user);  // Lưu lại người dùng với trạng thái đã xác thực

            return true;
        }

        return false;
    }
}
