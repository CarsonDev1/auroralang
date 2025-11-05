package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.SpeakingTestAttempt;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpeakingTestAttemptRepository extends JpaRepository<SpeakingTestAttempt, Integer> {
    List<SpeakingTestAttempt> findByUser_UserIdAndTest_TestIdOrderByAttemptNumberDesc(Integer userId, Integer testId);
    Optional<SpeakingTestAttempt> findByUser_UserIdAndTest_TestIdAndAttemptNumber(Integer userId, Integer testId, Integer attemptNumber);
    
    @Query("SELECT COALESCE(MAX(a.attemptNumber), 0) FROM SpeakingTestAttempt a WHERE a.user.userId = :userId AND a.test.testId = :testId")
    Integer findMaxAttemptNumber(@Param("userId") Integer userId, @Param("testId") Integer testId);
}

