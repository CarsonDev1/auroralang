package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.SessionAttendance;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionAttendanceRepository extends JpaRepository<SessionAttendance, Integer> {
    List<SessionAttendance> findBySession_SessionId(Integer sessionId);
    List<SessionAttendance> findByUser_UserId(Integer userId);
    Optional<SessionAttendance> findBySession_SessionIdAndUser_UserId(Integer sessionId, Integer userId);
    
    @Query("SELECT sa FROM SessionAttendance sa WHERE sa.session.classEntity.classId = :classId " +
           "AND sa.user.userId = :userId")
    List<SessionAttendance> findByClassAndUser(@Param("classId") Integer classId, @Param("userId") Integer userId);
}

