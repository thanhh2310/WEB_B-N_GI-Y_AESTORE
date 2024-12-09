/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.TokenRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.response.TokenResponse;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.WebErrorConfig;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.respository.UserRepository;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.impl.AAD;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    @Value("${jwt.singerKey}")
    private String singerKey;

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jWTClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("AE_STORE")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jWTClaimsSet.toJSONObject());
        JWSObject jWSObject = new JWSObject(header, payload);
        try {
            jWSObject.sign(new MACSigner(singerKey.getBytes()));
            return jWSObject.serialize();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    String buildScope(User user) {
        String result = "";
        if (!user.getRoles().isEmpty()) {
            for (Role role : user.getRoles()) {
                result += role.getName() + " ";
            }
        }
        return result.trim();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest requestAuthenticate, HttpSession session) {
        var user = userRepository.findByUsername(requestAuthenticate.getUsername()).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean check = passwordEncoder.matches(requestAuthenticate.getPassword(), user.getPassword());
        // Kiểm tra xem người dùng đã xác thực email chưa
        if (!user.isEmailVerified()) {
            throw new WebErrorConfig(ErrorCode.EMAIL_NOT_VERIFIED);
        }
        if (!check) {
            throw new WebErrorConfig(ErrorCode.WRONG_PASSWORD);
        }

        var token = generateToken(user);
        session.setAttribute("token", token);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticate(true)
                .build();

    }

    public TokenResponse veryfier(TokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(singerKey);
        SignedJWT singedJwt = SignedJWT.parse(token);
        var check = singedJwt.verify(verifier);

        Date expityTime = singedJwt.getJWTClaimsSet().getExpirationTime();
        return TokenResponse.builder()
                .valid(check && expityTime.after(new Date()))
                .build();

    }

    public String getUsernameFromToken(String token) throws ParseException, JOSEException {
        // Giải mã JWT
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Xác minh token bằng khóa bí mật (sử dụng MACVerifier với khóa secret)
        JWSVerifier verifier = new MACVerifier(singerKey);
        boolean isVerified = signedJWT.verify(verifier);

        if (!isVerified) {
            throw new WebErrorConfig(ErrorCode.INVALID_TOKEN);  // Nếu token không hợp lệ
        }

        // Trích xuất 'subject' (username) từ claims
        String username = signedJWT.getJWTClaimsSet().getSubject();  // 'sub' là tên người dùng (username)

        if (username == null || username.isEmpty()) {
            throw new WebErrorConfig(ErrorCode.INVALID_TOKEN);  // Nếu không có username trong token
        }

        return username;  // Trả về username
    }

}
