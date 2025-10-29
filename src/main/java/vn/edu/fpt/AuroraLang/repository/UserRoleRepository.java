package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.UserRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUser_UserId(Integer userId);
    Optional<UserRole> findByUser_UserIdAndRole_RoleId(Integer userId, Integer roleId);
    void deleteByUser_UserIdAndRole_RoleId(Integer userId, Integer roleId);
}

