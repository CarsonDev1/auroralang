package vn.edu.fpt.AuroraLang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingTestAnswerResponse {
    private Integer answerId;
    private Integer questionId;
    private String questionText;
    private String audioUrl;
    private String transcribedText;
    private BigDecimal score;
    private BigDecimal accuracyScore;
    private BigDecimal fluencyScore;
    private String feedback;
    private Integer durationSeconds;
}

