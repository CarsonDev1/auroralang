package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.TestQuestion;

import java.util.List;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, Integer> {
    List<TestQuestion> findByTest_TestIdOrderByQuestionOrderAsc(Integer testId);
    Long countByTest_TestId(Integer testId);
}

