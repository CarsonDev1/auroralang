package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.UserTestAttempt;

import java.util.List;

@Repository
public interface UserTestAttemptRepository extends JpaRepository<UserTestAttempt, Integer> {
    Page<UserTestAttempt> findByUser_UserId(Integer userId, Pageable pageable);
    List<UserTestAttempt> findByUser_UserIdAndTest_TestIdOrderByAttemptNumberDesc(Integer userId, Integer testId);
    
    @Query("SELECT COUNT(uta) FROM UserTestAttempt uta WHERE uta.user.userId = :userId AND uta.test.testId = :testId")
    Long countAttemptsByUserAndTest(@Param("userId") Integer userId, @Param("testId") Integer testId);
    
    @Query("SELECT uta FROM UserTestAttempt uta WHERE uta.user.userId = :userId AND uta.test.testId = :testId " +
           "ORDER BY uta.score DESC")
    List<UserTestAttempt> findBestAttempt(@Param("userId") Integer userId, @Param("testId") Integer testId, Pageable pageable);
}

