package vn.edu.fpt.AuroraLang.service;

import java.util.Map;

import vn.edu.fpt.AuroraLang.dto.request.PaymentRequest;
import vn.edu.fpt.AuroraLang.dto.response.VnPayResponse;

public interface VnPayService {
  VnPayResponse createPaymentUrl(PaymentRequest request, String clientIp);

  String processPaymentReturn(Map<String, String> vnpParams);
}
