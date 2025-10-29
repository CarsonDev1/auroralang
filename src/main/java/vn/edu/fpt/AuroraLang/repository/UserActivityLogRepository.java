package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.UserActivityLog;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
    Page<UserActivityLog> findByUser_UserId(Integer userId, Pageable pageable);
    Page<UserActivityLog> findByActivityType(String activityType, Pageable pageable);
    List<UserActivityLog> findByUser_UserIdAndCreatedAtBetween(
        Integer userId, 
        LocalDateTime startDate, 
        LocalDateTime endDate
    );
}

