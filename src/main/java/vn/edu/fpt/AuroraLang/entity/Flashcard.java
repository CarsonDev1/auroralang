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
@Table(name = "flashcards", indexes = {
    @Index(name = "idx_course_flashcards", columnList = "course_id"),
    @Index(name = "idx_user_flashcards", columnList = "user_id"),
    @Index(name = "idx_difficulty", columnList = "difficulty")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flashcard_id")
    private Integer flashcardId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "front_text", nullable = false, length = 500)
    private String frontText;
    
    @Column(name = "back_text", nullable = false, columnDefinition = "TEXT")
    private String backText;
    
    @Column(name = "front_audio_url", length = 500)
    private String frontAudioUrl;
    
    @Column(name = "back_audio_url", length = 500)
    private String backAudioUrl;
    
    @Column(name = "front_image_url", length = 500)
    private String frontImageUrl;
    
    @Column(name = "back_image_url", length = 500)
    private String backImageUrl;
    
    @Column(name = "pronunciation")
    private String pronunciation;
    
    @Column(name = "example_sentence", columnDefinition = "TEXT")
    private String exampleSentence;
    
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty = Difficulty.MEDIUM;
    
    @Column(name = "tags", length = 500)
    private String tags;
    
    @Column(name = "is_public")
    private Boolean isPublic = false;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "flashcard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserFlashcardProgress> progresses = new HashSet<>();
    
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
}

