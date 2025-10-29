package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequest {
    
    @NotBlank(message = "Lesson code is required")
    private String lessonCode;
    
    @NotBlank(message = "Lesson name is required")
    private String lessonName;
    
    private String description;
    private String content;
    private String videoUrl;
    private String audioUrl;
    private String documentUrl;
    
    @NotNull(message = "Lesson order is required")
    private Integer lessonOrder;
    
    private Integer durationMinutes;
    private String lessonType; // VIDEO, READING, AUDIO, INTERACTIVE, FLASHCARD, TEST
    private Boolean isPreview;
    private Boolean isActive;
}

