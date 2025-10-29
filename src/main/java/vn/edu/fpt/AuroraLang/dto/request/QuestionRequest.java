package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    
    @NotNull(message = "Question type ID is required")
    private Integer questionTypeId;
    
    @NotBlank(message = "Question text is required")
    private String questionText;
    
    private String questionImageUrl;
    private String questionAudioUrl;
    private BigDecimal points;
    private String difficulty; // EASY, MEDIUM, HARD
    private String explanation;
    private String tags;
    private Boolean isActive;
    
    private List<QuestionOptionRequest> options;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionOptionRequest {
        private String optionText;
        private String optionImageUrl;
        private Boolean isCorrect;
        private Integer optionOrder;
    }
}

