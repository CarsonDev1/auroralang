package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_study_logs", 
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_date", columnNames = {"user_id", "study_date"})
    },
    indexes = {
        @Index(name = "idx_study_date", columnList = "study_date")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyStudyLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "study_date", nullable = false)
    private LocalDate studyDate;
    
    @Column(name = "lessons_completed")
    private Integer lessonsCompleted = 0;
    
    @Column(name = "flashcards_reviewed")
    private Integer flashcardsReviewed = 0;
    
    @Column(name = "tests_taken")
    private Integer testsTaken = 0;
    
    @Column(name = "time_spent_seconds")
    private Integer timeSpentSeconds = 0;
    
    @Column(name = "xp_earned")
    private Integer xpEarned = 0;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

