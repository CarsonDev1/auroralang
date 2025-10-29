package vn.edu.fpt.AuroraLang.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.AuroraLang.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCourseCode(String courseCode);
    
    Page<Course> findByIsPublicAndIsActiveAndStatus(
        Boolean isPublic, 
        Boolean isActive, 
        Course.Status status, 
        Pageable pageable
    );
    
    Page<Course> findByCreatedBy_UserId(Integer userId, Pageable pageable);
    
    Page<Course> findByCategory_CategoryId(Integer categoryId, Pageable pageable);
    
    Page<Course> findByLevel(Course.Level level, Pageable pageable);
    
    @Query("SELECT c FROM Course c WHERE c.isActive = true AND c.status = 'PUBLISHED' " +
           "AND (c.courseName LIKE %:keyword% OR c.description LIKE %:keyword%)")
    Page<Course> searchPublicCourses(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT c FROM Course c WHERE (c.courseName LIKE %:keyword% OR c.courseCode LIKE %:keyword% OR c.description LIKE %:keyword%)")
    Page<Course> searchAllCourses(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT c FROM Course c WHERE c.createdBy.userId = :expertId " +
           "AND (c.courseName LIKE %:keyword% OR c.courseCode LIKE %:keyword%)")
    Page<Course> searchExpertCourses(@Param("expertId") Integer expertId, 
                                     @Param("keyword") String keyword, 
                                     Pageable pageable);
    
    List<Course> findTop10ByIsPublicAndIsActiveAndStatusOrderByTotalEnrollmentsDesc(
        Boolean isPublic, 
        Boolean isActive, 
        Course.Status status
    );
}

