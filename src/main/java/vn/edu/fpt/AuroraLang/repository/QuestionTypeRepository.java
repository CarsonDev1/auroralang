package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.QuestionType;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Integer> {
    Optional<QuestionType> findByTypeName(String typeName);
    List<QuestionType> findByIsActive(Boolean isActive);
}

