package vn.edu.fpt.AuroraLang.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.AuroraLang.entity.ClassEnrollment;

@Repository
public interface ClassEnrollmentRepository extends JpaRepository<ClassEnrollment, Integer> {
    Optional<ClassEnrollment> findByUser_UserIdAndClassEntity_ClassId(Integer userId, Integer classId);
    Page<ClassEnrollment> findByUser_UserId(Integer userId, Pageable pageable);
    Page<ClassEnrollment> findByClassEntity_ClassId(Integer classId, Pageable pageable);
    Page<ClassEnrollment> findByClassEntity_ClassIdAndStatus(Integer classId, ClassEnrollment.Status status, Pageable pageable);
    
    @Query("SELECT COUNT(ce) FROM ClassEnrollment ce WHERE ce.classEntity.classId = :classId AND ce.status = 'ACTIVE'")
    Long countActiveByClassId(@Param("classId") Integer classId);
    
    boolean existsByUser_UserIdAndClassEntity_ClassId(Integer userId, Integer classId);
    
    @Query("SELECT ce FROM ClassEnrollment ce WHERE ce.classEntity.manager.userId = :managerId")
    Page<ClassEnrollment> findByManagerId(@Param("managerId") Integer managerId, Pageable pageable);
}

