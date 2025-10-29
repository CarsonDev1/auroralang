package vn.edu.fpt.AuroraLang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse {
    private Integer testId;
    private String testCode;
    private String testName;
    private String description;
    private String testType;
    private Integer durationMinutes;
    private BigDecimal passingScore;
    private BigDecimal totalPoints;
    private Integer maxAttempts;
    private Boolean shuffleQuestions;
    private Boolean shuffleOptions;
    private Boolean showCorrectAnswers;
    private LocalDateTime availableFrom;
    private LocalDateTime availableUntil;
    private Boolean isActive;
    private Integer totalQuestions;
}

