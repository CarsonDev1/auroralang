package vn.edu.fpt.AuroraLang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "vnpay")
@Data
public class VnPayConfig {
  private String version;
  private String tmnCode;
  private String hashSecret;
  private String paymentUrl;
  private String returnUrl;
  private String apiUrl;
  private String command;
  private String currCode;
  private String locale;
}
