package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "test_questions", uniqueConstraints = {
    @UniqueConstraint(name = "unique_test_question", columnNames = {"test_id", "question_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_question_id")
    private Integer testQuestionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    
    @Column(name = "question_order")
    private Integer questionOrder = 0;
    
    @Column(name = "points_override", precision = 5, scale = 2)
    private BigDecimal pointsOverride;
}

