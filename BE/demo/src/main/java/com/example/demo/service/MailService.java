    package com.example.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    @Value("${spring.mail.from}")
    private String emailFrom; // Lấy thông tin email từ application.properties
    private final JavaMailSender mailSender;

    // Phương thức gửi email chỉ chứa đường link xác nhận
    public String  sendEmail(String toEmail,String token) throws MessagingException {
        log.info("Send email");
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");
        helper.setFrom(emailFrom);
        helper.setTo(toEmail);
        String verificationLink = "http://localhost:8081/saleShoes/users/verify?token=" + token;  // Địa chỉ xác nhận email
        String subject = "Email Verification";
        String content = "Please click the link below to verify your email:\n\n" + verificationLink;
        helper.setSubject(subject);
        helper.setText(content,true);
        mailSender.send(message);
        log.info("Send Success");
        return "sent";
        
        
    }
    
}
