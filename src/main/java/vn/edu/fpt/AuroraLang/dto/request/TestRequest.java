package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {
    
    @NotBlank(message = "Test code is required")
    private String testCode;
    
    @NotBlank(message = "Test name is required")
    private String testName;
    
    private String description;
    private String testType; // QUIZ, MIDTERM, FINAL, PRACTICE
    
    @NotNull(message = "Duration is required")
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
    
    private List<Integer> questionIds;
}

