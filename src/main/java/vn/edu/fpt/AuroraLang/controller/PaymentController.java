package vn.edu.fpt.AuroraLang.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.request.PaymentRequest;
import vn.edu.fpt.AuroraLang.dto.response.VnPayResponse;
import vn.edu.fpt.AuroraLang.service.VnPayService;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final VnPayService vnPayService;

  @PostMapping("/create")
  public ResponseEntity<ApiResponse<VnPayResponse>> createPayment(@Valid @RequestBody PaymentRequest request,
      HttpServletRequest httpRequest) {
    String clientIp = getClientIp(httpRequest);
    VnPayResponse res = vnPayService.createPaymentUrl(request, clientIp);
    return ResponseEntity.ok(ApiResponse.success("VNPay URL created", res));
  }

  @GetMapping("/vnpay-return")
  public ResponseEntity<String> vnpayReturn(HttpServletRequest request) {
    Map<String, String> params = new HashMap<>();
    Enumeration<String> names = request.getParameterNames();
    while (names.hasMoreElements()) {
      String name = names.nextElement();
      params.put(name, request.getParameter(name));
    }
    String result = vnPayService.processPaymentReturn(params);
    return ResponseEntity.ok(result);
  }

  private static String getClientIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.isBlank()) {
      ip = request.getRemoteAddr();
    } else if (ip.contains(",")) {
      ip = ip.split(",")[0].trim();
    }
    return ip;
  }
}
