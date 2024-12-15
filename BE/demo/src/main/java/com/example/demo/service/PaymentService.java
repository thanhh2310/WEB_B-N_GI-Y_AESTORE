package com.example.demo.service;

import com.example.demo.Config.VnpayConfig;
import com.example.demo.dto.response.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class PaymentService {

    public PaymentResponse createPayment(Integer userId, HttpServletRequest req) {
        String orderType = "other";

        long amount = 1000 * 100;

        log.info("Received userId: {}", userId);

        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnpayConfig.getIpAddress(req);

        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_app_user_id", userId + "");

        vnp_Params.put("vnp_BankCode", "NCB");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");

        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Log vnp_Params để kiểm tra tất cả các tham số
        log.info("vnp_Params before signing: {}", vnp_Params);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName).append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException ex) {
                    log.error("Error encoding value for field {}: {}", fieldName, ex.getMessage());
                }

                // Build query
                try {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException ex) {
                    log.error("Error encoding field name {}: {}", fieldName, ex.getMessage());
                }
                query.append('=');
                try {
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException ex) {
                    log.error("Error encoding field value {}: {}", fieldValue, ex.getMessage());
                }

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        // Log hashData và query trước khi tính toán secure hash
        log.info("hashData: {}", hashData.toString());
        log.info("queryUrl before adding secure hash: {}", query.toString());

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());

        // Log secure hash
        log.info("vnp_SecureHash: {}", vnp_SecureHash);

        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + query.toString();

        // Log final payment URL
        log.info("Payment URL: {}", paymentUrl);

        // Trả về response chứa URL thanh toán
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setMessage("Success");
        paymentResponse.setStatus("OK");
        paymentResponse.setUrl(paymentUrl); // Trả về URL thanh toán
        return paymentResponse;

    }
}
