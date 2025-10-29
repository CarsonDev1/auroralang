package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tests", 
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_course_test", columnNames = {"course_id", "test_code"})
    },
    indexes = {
        @Index(name = "idx_available_tests", columnList = "is_active, available_from, available_until")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Integer testId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @Column(name = "test_code", nullable = false, length = 50)
    private String testCode;
    
    @Column(name = "test_name", nullable = false)
    private String testName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "test_type")
    private TestType testType = TestType.QUIZ;
    
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes = 60;
    
    @Column(name = "passing_score", precision = 5, scale = 2)
    private BigDecimal passingScore = new BigDecimal("70.00");
    
    @Column(name = "total_points", precision = 8, scale = 2)
    private BigDecimal totalPoints = new BigDecimal("100.00");
    
    @Column(name = "max_attempts")
    private Integer maxAttempts = 0; // 0 = unlimited
    
    @Column(name = "shuffle_questions")
    private Boolean shuffleQuestions = false;
    
    @Column(name = "shuffle_options")
    private Boolean shuffleOptions = false;
    
    @Column(name = "show_correct_answers")
    private Boolean showCorrectAnswers = true;
    
    @Column(name = "available_from")
    private LocalDateTime availableFrom;
    
    @Column(name = "available_until")
    private LocalDateTime availableUntil;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TestQuestion> testQuestions = new HashSet<>();
    
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserTestAttempt> attempts = new HashSet<>();
    
    public enum TestType {
        QUIZ, MIDTERM, FINAL, PRACTICE
    }
}

