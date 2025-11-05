package vn.edu.fpt.AuroraLang.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.AuroraLang.entity.UserFlashcardProgress;

@Repository
public interface UserFlashcardProgressRepository extends JpaRepository<UserFlashcardProgress, Integer> {
    Optional<UserFlashcardProgress> findByUser_UserIdAndFlashcard_FlashcardId(Integer userId, Integer flashcardId);
    List<UserFlashcardProgress> findByUser_UserId(Integer userId);
    
    @Query("SELECT ufp FROM UserFlashcardProgress ufp WHERE ufp.user.userId = :userId " +
           "AND ufp.nextReviewAt <= :now ORDER BY ufp.nextReviewAt ASC")
    List<UserFlashcardProgress> findDueFlashcards(@Param("userId") Integer userId, 
                                                   @Param("now") LocalDateTime now);
    
    @Query("SELECT COUNT(DISTINCT ufp.flashcard.flashcardId) FROM UserFlashcardProgress ufp " +
           "WHERE ufp.user.userId = :userId AND ufp.lastReviewedAt IS NOT NULL")
    Long countStudiedFlashcards(@Param("userId") Integer userId);
}

