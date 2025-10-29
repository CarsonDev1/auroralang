package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    Page<Test> findByCourse_CourseId(Integer courseId, Pageable pageable);
    Optional<Test> findByCourse_CourseIdAndTestCode(Integer courseId, String testCode);
    
    @Query("SELECT t FROM Test t WHERE t.course.courseId = :courseId " +
           "AND t.isActive = true " +
           "AND (t.availableFrom IS NULL OR t.availableFrom <= :now) " +
           "AND (t.availableUntil IS NULL OR t.availableUntil >= :now)")
    List<Test> findAvailableTests(@Param("courseId") Integer courseId, @Param("now") LocalDateTime now);
    
    Long countByCourse_CourseId(Integer courseId);
}

