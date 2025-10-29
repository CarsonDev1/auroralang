package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.UserAnswer;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByAttempt_AttemptId(Integer attemptId);
    List<UserAnswer> findByAttempt_AttemptIdOrderByQuestion_QuestionIdAsc(Integer attemptId);
}

