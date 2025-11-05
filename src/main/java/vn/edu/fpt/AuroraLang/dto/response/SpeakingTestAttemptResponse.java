package vn.edu.fpt.AuroraLang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingTestAttemptResponse {
    private Integer attemptId;
    private Integer testId;
    private String testName;
    private Integer attemptNumber;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private Integer timeSpentSeconds;
    private BigDecimal score;
    private BigDecimal percentage;
    private String status;
    private Boolean isPassed;
    private List<SpeakingTestAnswerResponse> answers;
}

