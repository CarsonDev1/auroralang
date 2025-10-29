package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.QuestionOption;

import java.util.List;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Integer> {
    List<QuestionOption> findByQuestion_QuestionIdOrderByOptionOrderAsc(Integer questionId);
    List<QuestionOption> findByQuestion_QuestionIdAndIsCorrect(Integer questionId, Boolean isCorrect);
}

