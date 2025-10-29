package vn.edu.fpt.AuroraLang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
    private Integer lessonId;
    private String lessonCode;
    private String lessonName;
    private String description;
    private String content;
    private String videoUrl;
    private String audioUrl;
    private String documentUrl;
    private Integer lessonOrder;
    private Integer durationMinutes;
    private String lessonType;
    private Boolean isPreview;
    private Boolean isActive;
}

