package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.FlashcardLesson;

import java.util.List;

@Repository
public interface FlashcardLessonRepository extends JpaRepository<FlashcardLesson, Integer> {
    List<FlashcardLesson> findByCourse_CourseIdAndIsActive(Integer courseId, Boolean isActive);
    List<FlashcardLesson> findByLesson_LessonId(Integer lessonId);
}

