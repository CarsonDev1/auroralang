package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.ClassSession;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, Integer> {
    List<ClassSession> findByClassEntity_ClassIdOrderBySessionDateAsc(Integer classId);
    Page<ClassSession> findByClassEntity_ClassId(Integer classId, Pageable pageable);
    List<ClassSession> findByClassEntity_ClassIdAndSessionDateBetween(
        Integer classId, 
        LocalDate startDate, 
        LocalDate endDate
    );
}

