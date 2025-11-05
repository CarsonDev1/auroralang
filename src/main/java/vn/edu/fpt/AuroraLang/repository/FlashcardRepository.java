package vn.edu.fpt.AuroraLang.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.fpt.AuroraLang.entity.Flashcard;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    Page<Flashcard> findByCourse_CourseId(Integer courseId, Pageable pageable);
    Page<Flashcard> findByUser_UserId(Integer userId, Pageable pageable);
    List<Flashcard> findByCourse_CourseIdAndUser_UserId(Integer courseId, Integer userId);
    
    @Query("SELECT f FROM Flashcard f WHERE f.user.userId = :userId " +
           "AND (f.frontText LIKE %:keyword% OR f.backText LIKE %:keyword%)")
    Page<Flashcard> searchUserFlashcards(@Param("userId") Integer userId, 
                                         @Param("keyword") String keyword, 
                                         Pageable pageable);
    
    Long countByCourse_CourseId(Integer courseId);
    Long countByUser_UserId(Integer userId);
}

