package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.Lesson;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByCourse_CourseIdOrderByLessonOrderAsc(Integer courseId);
    Page<Lesson> findByCourse_CourseId(Integer courseId, Pageable pageable);
    Optional<Lesson> findByCourse_CourseIdAndLessonCode(Integer courseId, String lessonCode);
    List<Lesson> findByCourse_CourseIdAndIsActive(Integer courseId, Boolean isActive);
    Long countByCourse_CourseId(Integer courseId);
}

