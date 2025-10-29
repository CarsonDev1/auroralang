package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lessons", 
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_course_lesson", columnNames = {"course_id", "lesson_code"})
    },
    indexes = {
        @Index(name = "idx_lesson_order", columnList = "course_id, lesson_order")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Integer lessonId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @Column(name = "lesson_code", nullable = false, length = 50)
    private String lessonCode;
    
    @Column(name = "lesson_name", nullable = false)
    private String lessonName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "video_url", length = 500)
    private String videoUrl;
    
    @Column(name = "audio_url", length = 500)
    private String audioUrl;
    
    @Column(name = "document_url", length = 500)
    private String documentUrl;
    
    @Column(name = "lesson_order", nullable = false)
    private Integer lessonOrder = 0;
    
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_type")
    private LessonType lessonType = LessonType.READING;
    
    @Column(name = "is_preview")
    private Boolean isPreview = false;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonComment> comments = new HashSet<>();
    
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonProgress> progresses = new HashSet<>();
    
    public enum LessonType {
        VIDEO, READING, AUDIO, INTERACTIVE, FLASHCARD, TEST
    }
}

