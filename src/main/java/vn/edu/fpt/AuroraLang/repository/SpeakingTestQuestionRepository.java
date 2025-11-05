package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.SpeakingTestQuestion;

import java.util.List;

@Repository
public interface SpeakingTestQuestionRepository extends JpaRepository<SpeakingTestQuestion, Integer> {
    List<SpeakingTestQuestion> findByTest_TestIdOrderByQuestionOrderAsc(Integer testId);
    List<SpeakingTestQuestion> findByTest_TestIdAndIsActiveTrueOrderByQuestionOrderAsc(Integer testId);
}

