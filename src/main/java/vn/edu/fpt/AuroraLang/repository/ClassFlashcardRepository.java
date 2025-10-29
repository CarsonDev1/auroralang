package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.ClassFlashcard;

import java.util.List;

@Repository
public interface ClassFlashcardRepository extends JpaRepository<ClassFlashcard, Integer> {
    List<ClassFlashcard> findByClassEntity_ClassId(Integer classId);
    Page<ClassFlashcard> findByClassEntity_ClassIdAndIsActive(Integer classId, Boolean isActive, Pageable pageable);
}

