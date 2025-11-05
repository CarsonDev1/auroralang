package vn.edu.fpt.AuroraLang.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "speaking_test_questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingTestQuestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
    
    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;
    
    @Column(name = "expected_answer", columnDefinition = "TEXT")
    private String expectedAnswer; // Reference answer for evaluation
    
    @Column(name = "audio_url")
    private String audioUrl; // Optional: audio prompt
    
    @Column(name = "time_limit_seconds")
    private Integer timeLimitSeconds = 60; // Default 60 seconds per question
    
    @Column(name = "question_order")
    private Integer questionOrder;
    
    @Column(name = "points", precision = 5, scale = 2)
    private java.math.BigDecimal points = java.math.BigDecimal.valueOf(10.00);
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}

