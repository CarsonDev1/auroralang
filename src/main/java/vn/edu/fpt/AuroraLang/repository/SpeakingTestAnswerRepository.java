package vn.edu.fpt.AuroraLang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.AuroraLang.entity.SpeakingTestAnswer;

@Repository
public interface SpeakingTestAnswerRepository extends JpaRepository<SpeakingTestAnswer, Integer> {
    List<SpeakingTestAnswer> findByAttempt_AttemptIdOrderByCreatedAtAsc(Integer attemptId);
}

