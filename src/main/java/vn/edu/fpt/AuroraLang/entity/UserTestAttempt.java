package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_test_attempts", indexes = {
    @Index(name = "idx_user_attempts", columnList = "user_id, test_id"),
    @Index(name = "idx_attempt_status", columnList = "status"),
    @Index(name = "idx_test_attempts_score", columnList = "user_id, test_id, score")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTestAttempt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Integer attemptId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
    
    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber;
    
    @CreationTimestamp
    @Column(name = "start_time", nullable = false, updatable = false)
    private LocalDateTime startTime;
    
    @Column(name = "submit_time")
    private LocalDateTime submitTime;
    
    @Column(name = "time_spent_seconds")
    private Integer timeSpentSeconds;
    
    @Column(name = "score", precision = 8, scale = 2)
    private BigDecimal score;
    
    @Column(name = "percentage", precision = 5, scale = 2)
    private BigDecimal percentage;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.IN_PROGRESS;
    
    @Column(name = "is_passed")
    private Boolean isPassed = false;
    
    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAnswer> answers = new HashSet<>();
    
    public enum Status {
        IN_PROGRESS, SUBMITTED, GRADED, ABANDONED
    }
}

