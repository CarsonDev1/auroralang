package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_flashcard_progress", 
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_flashcard", columnNames = {"user_id", "flashcard_id"})
    },
    indexes = {
        @Index(name = "idx_next_review", columnList = "user_id, next_review_at")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFlashcardProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Integer progressId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id", nullable = false)
    private Flashcard flashcard;
    
    @Column(name = "mastery_level")
    private Integer masteryLevel = 0; // 0-5 scale
    
    @Column(name = "correct_count")
    private Integer correctCount = 0;
    
    @Column(name = "incorrect_count")
    private Integer incorrectCount = 0;
    
    @Column(name = "last_reviewed_at")
    private LocalDateTime lastReviewedAt;
    
    @Column(name = "next_review_at")
    private LocalDateTime nextReviewAt;
    
    @Column(name = "ease_factor", precision = 3, scale = 2)
    private BigDecimal easeFactor = new BigDecimal("2.50");
    
    @Column(name = "interval_days")
    private Integer intervalDays = 0;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

