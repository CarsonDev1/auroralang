package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardRequest {
    
    @NotBlank(message = "Front text is required")
    private String frontText;
    
    @NotBlank(message = "Back text is required")
    private String backText;
    
    private String frontAudioUrl;
    private String backAudioUrl;
    private String frontImageUrl;
    private String backImageUrl;
    private String pronunciation;
    private String exampleSentence;
    private String note;
    private String difficulty; // EASY, MEDIUM, HARD
    private String tags;
    private Boolean isPublic;
}

