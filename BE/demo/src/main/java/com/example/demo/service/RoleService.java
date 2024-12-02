/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Role;
import com.example.demo.respository.RoleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleResponse create(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());

        role = roleRepository.save(role);
        return RoleResponse.builder()
                .name(request.getName())
                .build();
    }

    public List<Role> getAll() {
        // Lấy tất cả các Role từ cơ sở dữ liệu
        return roleRepository.findByActiveTrue();

        // Chuyển đổi danh sách Role thành danh sách RoleResponse và trả về
    }
//    public void update(Integer Id){
//         Role role =roleRepository.findById(Id).orElseThrow(()->new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));
//         if(!role.isActive()) throw new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND);
//         
//    }

}
