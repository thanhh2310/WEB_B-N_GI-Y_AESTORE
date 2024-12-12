/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.dto.request.AddressRequest;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.respository.AddressRepository;
import com.example.demo.respository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class AddressService {
     private final UserRepository userRepository;
     private final AddressRepository addresRepository;
    public Address create(AddressRequest request){
        User user =userRepository.findById(request.getUserId()).orElseThrow(()-> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        Address newAddress=new Address();
        newAddress.setAddress(request.getAddress());
        newAddress.setUser(user);
        addresRepository.save(newAddress);
        return newAddress;
    }
    public List<Address> getAllByUser(Integer userId){
        User user =userRepository.findById(userId).orElseThrow(()-> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        return addresRepository.findAddressByUser(user);
    }
}
