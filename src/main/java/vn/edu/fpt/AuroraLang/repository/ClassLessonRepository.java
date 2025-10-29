package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.ClassLesson;

import java.util.List;

@Repository
public interface ClassLessonRepository extends JpaRepository<ClassLesson, Integer> {
    List<ClassLesson> findByClassEntity_ClassIdOrderByLessonOrderAsc(Integer classId);
    List<ClassLesson> findByClassEntity_ClassIdAndIsActive(Integer classId, Boolean isActive);
}

