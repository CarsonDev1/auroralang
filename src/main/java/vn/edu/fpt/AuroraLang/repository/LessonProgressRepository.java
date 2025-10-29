package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.LessonProgress;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Integer> {
    Optional<LessonProgress> findByUser_UserIdAndLesson_LessonId(Integer userId, Integer lessonId);
    List<LessonProgress> findByUser_UserId(Integer userId);
    
    @Query("SELECT lp FROM LessonProgress lp WHERE lp.user.userId = :userId " +
           "AND lp.lesson.course.courseId = :courseId")
    List<LessonProgress> findByUserAndCourse(@Param("userId") Integer userId, @Param("courseId") Integer courseId);
    
    @Query("SELECT COUNT(lp) FROM LessonProgress lp WHERE lp.user.userId = :userId " +
           "AND lp.lesson.course.courseId = :courseId AND lp.status = 'COMPLETED'")
    Long countCompletedLessonsByUserAndCourse(@Param("userId") Integer userId, @Param("courseId") Integer courseId);
}

