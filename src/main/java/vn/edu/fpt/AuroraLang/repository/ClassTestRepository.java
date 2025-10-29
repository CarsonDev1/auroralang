package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.ClassTest;

import java.util.List;

@Repository
public interface ClassTestRepository extends JpaRepository<ClassTest, Integer> {
    List<ClassTest> findByClassEntity_ClassId(Integer classId);
    Page<ClassTest> findByClassEntity_ClassIdAndIsActive(Integer classId, Boolean isActive, Pageable pageable);
}

