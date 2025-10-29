package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flashcard_lesson_items", uniqueConstraints = {
    @UniqueConstraint(name = "unique_lesson_flashcard", columnNames = {"flashcard_lesson_id", "flashcard_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardLessonItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_lesson_id", nullable = false)
    private FlashcardLesson flashcardLesson;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id", nullable = false)
    private Flashcard flashcard;
    
    @Column(name = "item_order")
    private Integer itemOrder = 0;
}

