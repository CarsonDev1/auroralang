package vn.edu.fpt.AuroraLang.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.config.VnPayConfig;
import vn.edu.fpt.AuroraLang.dto.request.PaymentRequest;
import vn.edu.fpt.AuroraLang.dto.response.VnPayResponse;
import vn.edu.fpt.AuroraLang.service.VnPayService;

@Service
@RequiredArgsConstructor
public class VnPayServiceImpl implements VnPayService {

  private final VnPayConfig config;

  @Override
  public VnPayResponse createPaymentUrl(PaymentRequest request, String clientIp) {
    String txnRef = String.valueOf(System.currentTimeMillis());

    Map<String, String> vnpParams = new TreeMap<>();
    vnpParams.put("vnp_Version", config.getVersion());
    vnpParams.put("vnp_Command", config.getCommand());
    vnpParams.put("vnp_TmnCode", config.getTmnCode());
    // amount must be integer in VND*100
    java.math.BigDecimal safeAmount = request.getAmount() == null ? java.math.BigDecimal.ZERO : request.getAmount();
    String amountStr = safeAmount.multiply(java.math.BigDecimal.valueOf(100))
        .setScale(0, java.math.RoundingMode.HALF_UP).toPlainString();
    vnpParams.put("vnp_Amount", amountStr);
    vnpParams.put("vnp_CurrCode", config.getCurrCode());
    vnpParams.put("vnp_TxnRef", txnRef);
    String orderInfo = request.getOrderInfo() != null ? request.getOrderInfo() : "Payment";
    orderInfo = orderInfo.replaceAll("[^A-Za-z0-9 _-]", " ").trim();
    if (orderInfo.length() > 255)
      orderInfo = orderInfo.substring(0, 255);
    vnpParams.put("vnp_OrderInfo", orderInfo);
    vnpParams.put("vnp_OrderType", "other");
    vnpParams.put("vnp_Locale", config.getLocale());
    vnpParams.put("vnp_ReturnUrl", config.getReturnUrl());
    vnpParams.put("vnp_IpAddr", clientIp != null ? clientIp : "127.0.0.1");

    // Use Vietnam timezone for VNPay timestamps
    java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    java.time.ZonedDateTime nowVn = java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Ho_Chi_Minh"));
    String createDate = nowVn.format(dtf);
    vnpParams.put("vnp_CreateDate", createDate);
    String expireDate = nowVn.plusMinutes(15).format(dtf);
    vnpParams.put("vnp_ExpireDate", expireDate);

    // Build query & secure hash
    String query = buildQuery(vnpParams);
    String hash = hmacSHA512(config.getHashSecret(), query);
    String paymentUrl = config.getPaymentUrl() + "?" + query + "&vnp_SecureHashType=HmacSHA512&vnp_SecureHash=" + hash;

    return VnPayResponse.builder()
        .paymentUrl(paymentUrl)
        .transactionRef(txnRef)
        .build();
  }

  @Override
  public String processPaymentReturn(Map<String, String> vnpParams) {
    String receivedHash = vnpParams.remove("vnp_SecureHash");
    vnpParams.remove("vnp_SecureHashType");
    String data = buildQuery(new TreeMap<>(vnpParams));
    String calcHash = hmacSHA512(config.getHashSecret(), data);

    if (!calcHash.equalsIgnoreCase(receivedHash)) {
      return "INVALID_SIGNATURE";
    }
    String responseCode = vnpParams.get("vnp_ResponseCode");
    return ("00".equals(responseCode)) ? "SUCCESS" : ("FAIL:" + responseCode);
  }

  private static String buildQuery(Map<String, String> params) {
    List<String> fieldNames = new ArrayList<>(params.keySet());
    Collections.sort(fieldNames);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < fieldNames.size(); i++) {
      String fieldName = fieldNames.get(i);
      String fieldValue = params.get(fieldName);
      if (fieldValue != null && fieldValue.length() > 0) {
        sb.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8))
            .append('=')
            .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
        if (i < fieldNames.size() - 1) {
          sb.append('&');
        }
      }
    }
    return sb.toString();
  }

  private static String hmacSHA512(String key, String data) {
    try {
      Mac hmac512 = Mac.getInstance("HmacSHA512");
      SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
      hmac512.init(secretKey);
      byte[] hashBytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder(2 * hashBytes.length);
      for (byte b : hashBytes) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (java.security.NoSuchAlgorithmException | java.security.InvalidKeyException e) {
      throw new RuntimeException("Error generating HMAC SHA512", e);
    }
  }
}
