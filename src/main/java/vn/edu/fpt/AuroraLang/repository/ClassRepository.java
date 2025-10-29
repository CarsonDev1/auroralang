package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.Class;

import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    Optional<Class> findByClassCode(String classCode);
    
    Page<Class> findByTeacher_UserId(Integer teacherId, Pageable pageable);
    Page<Class> findByManager_UserId(Integer managerId, Pageable pageable);
    Page<Class> findByCourse_CourseId(Integer courseId, Pageable pageable);
    Page<Class> findByIsPublicAndStatus(Boolean isPublic, Class.Status status, Pageable pageable);
    
    @Query("SELECT c FROM Class c WHERE c.teacher.userId = :teacherId " +
           "AND (c.className LIKE %:keyword% OR c.classCode LIKE %:keyword%)")
    Page<Class> searchTeacherClasses(@Param("teacherId") Integer teacherId, 
                                     @Param("keyword") String keyword, 
                                     Pageable pageable);
    
    @Query("SELECT c FROM Class c WHERE c.manager.userId = :managerId " +
           "AND (c.className LIKE %:keyword% OR c.classCode LIKE %:keyword%)")
    Page<Class> searchManagerClasses(@Param("managerId") Integer managerId, 
                                     @Param("keyword") String keyword, 
                                     Pageable pageable);
}

