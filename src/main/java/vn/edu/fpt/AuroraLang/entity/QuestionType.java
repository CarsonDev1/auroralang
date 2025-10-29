package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer typeId;
    
    @Column(name = "type_name", nullable = false, unique = true, length = 50)
    private String typeName; // MULTIPLE_CHOICE, TRUE_FALSE, FILL_BLANK, MATCHING, ESSAY
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "questionType", cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();
}

