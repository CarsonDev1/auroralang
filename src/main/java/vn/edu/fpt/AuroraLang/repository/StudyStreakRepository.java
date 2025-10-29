package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.StudyStreak;

import java.util.Optional;

@Repository
public interface StudyStreakRepository extends JpaRepository<StudyStreak, Integer> {
    Optional<StudyStreak> findByUser_UserId(Integer userId);
}

