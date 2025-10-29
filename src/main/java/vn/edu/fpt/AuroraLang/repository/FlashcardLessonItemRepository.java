package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.FlashcardLessonItem;

import java.util.List;

@Repository
public interface FlashcardLessonItemRepository extends JpaRepository<FlashcardLessonItem, Integer> {
    List<FlashcardLessonItem> findByFlashcardLesson_FlashcardLessonIdOrderByItemOrderAsc(Integer flashcardLessonId);
}

