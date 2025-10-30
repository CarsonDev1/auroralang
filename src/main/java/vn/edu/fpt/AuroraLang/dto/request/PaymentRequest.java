package vn.edu.fpt.AuroraLang.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {
  @NotNull
  private BigDecimal amount; // VND

  private String orderInfo; // description, include courseId if needed

  private Integer courseId; // optional context

  private String successRedirectUrl; // optional override
  private String cancelRedirectUrl; // optional
}
