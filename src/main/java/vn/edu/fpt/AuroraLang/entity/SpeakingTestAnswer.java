package vn.edu.fpt.AuroraLang.entity;

import java.math.BigDecimal;
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
@Table(name = "speaking_test_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingTestAnswer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Integer answerId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private SpeakingTestAttempt attempt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private SpeakingTestQuestion question;
    
    @Column(name = "audio_url", nullable = false, length = 500)
    private String audioUrl; // Path to recorded audio file
    
    @Column(name = "transcribed_text", columnDefinition = "TEXT")
    private String transcribedText; // Speech-to-text result
    
    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;
    
    @Column(name = "accuracy_score", precision = 5, scale = 2)
    private BigDecimal accuracyScore; // Pronunciation accuracy (0-100)
    
    @Column(name = "fluency_score", precision = 5, scale = 2)
    private BigDecimal fluencyScore; // Fluency score (0-100)
    
    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback; // AI feedback on pronunciation/grammar
    
    @Column(name = "duration_seconds")
    private Integer durationSeconds;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

