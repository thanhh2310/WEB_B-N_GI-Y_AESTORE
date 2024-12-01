/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Config;


import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.respository.RoleRepository;
import com.example.demo.respository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Admin
 */
@Configuration
public class ApplicationInitConfig {
    
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            // Tạo và lưu các role ADMIN và USER nếu chưa tồn tại
            Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setName("ADMIN");
                return roleRepository.save(role);
            });
            
             Role staffRole = roleRepository.findByName("STAFF").orElseGet(() -> {
                Role role = new Role();
                role.setName("STAFF");
                return roleRepository.save(role);
            });

            Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
                Role role = new Role();
                role.setName("USER");
                return roleRepository.save(role);
            });

            // Kiểm tra nếu người dùng "admin" chưa tồn tại trong cơ sở dữ liệu
            if (userRepository.findByUsername("admin").isEmpty()) {
                // Tạo người dùng admin với quyền ADMIN
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin"));  // Mã hóa mật khẩu
                adminUser.setEmail("admin@ae_store.com");
                adminUser.setRoles(new HashSet<>(Set.of(adminRole)));  // Gán role ADMIN cho user admin
                userRepository.save(adminUser);
            }

            // Kiểm tra nếu người dùng "user" chưa tồn tại trong cơ sở dữ liệu
           
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

