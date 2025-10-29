package vn.edu.fpt.AuroraLang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Integer courseId;
    private String courseCode;
    private String courseName;
    private String description;
    private String thumbnailUrl;
    private String categoryName;
    private String level;
    private String language;
    private Integer durationWeeks;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean isPublic;
    private String status;
    private Integer totalEnrollments;
    private Integer totalLessons;
    private Integer totalFlashcards;
    private Integer totalTests;
    private BigDecimal averageRating;
    private Integer totalReviews;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
}

