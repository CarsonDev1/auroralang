package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.CourseEnrollment;

import java.util.Optional;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Integer> {
    Optional<CourseEnrollment> findByUser_UserIdAndCourse_CourseId(Integer userId, Integer courseId);
    Page<CourseEnrollment> findByUser_UserId(Integer userId, Pageable pageable);
    Page<CourseEnrollment> findByUser_UserIdAndStatus(Integer userId, CourseEnrollment.Status status, Pageable pageable);
    Page<CourseEnrollment> findByCourse_CourseId(Integer courseId, Pageable pageable);
    Page<CourseEnrollment> findByCourse_CourseIdAndStatus(Integer courseId, CourseEnrollment.Status status, Pageable pageable);
    
    @Query("SELECT COUNT(ce) FROM CourseEnrollment ce WHERE ce.course.courseId = :courseId AND ce.status = 'ACTIVE'")
    Long countActiveByCourseId(@Param("courseId") Integer courseId);
    
    boolean existsByUser_UserIdAndCourse_CourseId(Integer userId, Integer courseId);
}

