package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.DailyStudyLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyStudyLogRepository extends JpaRepository<DailyStudyLog, Long> {
    Optional<DailyStudyLog> findByUser_UserIdAndStudyDate(Integer userId, LocalDate studyDate);
    List<DailyStudyLog> findByUser_UserIdAndStudyDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);
    List<DailyStudyLog> findByUser_UserIdOrderByStudyDateDesc(Integer userId);
}

