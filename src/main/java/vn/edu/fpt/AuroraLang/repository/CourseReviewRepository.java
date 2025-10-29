package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.CourseReview;

import java.util.Optional;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Integer> {
    Page<CourseReview> findByCourse_CourseId(Integer courseId, Pageable pageable);
    Page<CourseReview> findByCourse_CourseIdAndIsApproved(Integer courseId, Boolean isApproved, Pageable pageable);
    Optional<CourseReview> findByCourse_CourseIdAndUser_UserId(Integer courseId, Integer userId);
    
    @Query("SELECT AVG(cr.rating) FROM CourseReview cr WHERE cr.course.courseId = :courseId AND cr.isApproved = true")
    Double getAverageRatingByCourseId(@Param("courseId") Integer courseId);
    
    @Query("SELECT COUNT(cr) FROM CourseReview cr WHERE cr.course.courseId = :courseId AND cr.isApproved = true")
    Long countApprovedReviewsByCourseId(@Param("courseId") Integer courseId);
}

