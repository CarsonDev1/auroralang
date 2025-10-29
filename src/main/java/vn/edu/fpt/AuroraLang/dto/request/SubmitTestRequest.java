package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTestRequest {
    
    @NotNull(message = "Attempt ID is required")
    private Integer attemptId;
    
    private List<AnswerSubmission> answers;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerSubmission {
        private Integer questionId;
        private Integer selectedOptionId;
        private String answerText;
    }
}

