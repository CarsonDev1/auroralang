package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {
    
    @NotBlank(message = "Course code is required")
    private String courseCode;
    
    @NotBlank(message = "Course name is required")
    private String courseName;
    
    private String description;
    private String thumbnailUrl;
    private Integer categoryId;
    
    @NotNull(message = "Level is required")
    private String level; // BEGINNER, ELEMENTARY, INTERMEDIATE, ADVANCED, EXPERT
    
    private String language;
    private Integer durationWeeks;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean isPublic;
    private String status; // DRAFT, PUBLISHED, ARCHIVED
}

