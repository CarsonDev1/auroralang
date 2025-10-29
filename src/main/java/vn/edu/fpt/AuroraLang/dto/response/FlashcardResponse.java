package vn.edu.fpt.AuroraLang.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
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
    private Integer courseId;
    private String courseName;
    private Integer userId;
    private String userName;
    private LocalDateTime createdAt;
}