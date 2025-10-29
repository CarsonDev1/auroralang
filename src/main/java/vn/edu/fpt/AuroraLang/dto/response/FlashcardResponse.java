package vn.edu.fpt.AuroraLang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardResponse {
    private Integer flashcardId;
    private String frontText;
    private String backText;
    private String frontAudioUrl;
    private String backAudioUrl;
    private String frontImageUrl;
    private String backImageUrl;
    private String pronunciation;
    private String exampleSentence;
    private String note;
    private String difficulty;
    private String tags;
    private Boolean isPublic;
}

