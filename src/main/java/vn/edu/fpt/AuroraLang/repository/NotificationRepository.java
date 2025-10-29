package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Page<Notification> findByUser_UserIdOrderByCreatedAtDesc(Integer userId, Pageable pageable);
    Page<Notification> findByUser_UserIdAndIsReadOrderByCreatedAtDesc(Integer userId, Boolean isRead, Pageable pageable);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.userId = :userId AND n.isRead = false")
    Long countUnreadByUserId(@Param("userId") Integer userId);
}

