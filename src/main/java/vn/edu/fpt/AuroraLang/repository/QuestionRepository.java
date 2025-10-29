package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Page<Question> findByCourse_CourseId(Integer courseId, Pageable pageable);
    Page<Question> findByCourse_CourseIdAndIsActive(Integer courseId, Boolean isActive, Pageable pageable);
    Page<Question> findByCreatedBy_UserId(Integer userId, Pageable pageable);
    List<Question> findByCourse_CourseIdAndDifficulty(Integer courseId, Question.Difficulty difficulty);
    
    @Query("SELECT q FROM Question q WHERE q.course.courseId = :courseId " +
           "AND (q.questionText LIKE %:keyword% OR q.tags LIKE %:keyword%)")
    Page<Question> searchQuestions(@Param("courseId") Integer courseId, 
                                   @Param("keyword") String keyword, 
                                   Pageable pageable);
}

