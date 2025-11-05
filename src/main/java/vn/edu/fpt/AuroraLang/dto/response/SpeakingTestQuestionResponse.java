package vn.edu.fpt.AuroraLang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingTestQuestionResponse {
    private Integer questionId;
    private Integer testId;
    private String questionText;
    private String expectedAnswer;
    private String audioUrl;
    private Integer timeLimitSeconds;
    private Integer questionOrder;
    private BigDecimal points;
    private LocalDateTime createdAt;
}

