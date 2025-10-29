package vn.edu.fpt.AuroraLang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.entity.Role;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.entity.UserRole;
import vn.edu.fpt.AuroraLang.repository.RoleRepository;
import vn.edu.fpt.AuroraLang.repository.UserRepository;
import vn.edu.fpt.AuroraLang.repository.UserRoleRepository;
import vn.edu.fpt.AuroraLang.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
public class DebugController {
    
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Debug endpoint working");
        result.put("timestamp", System.currentTimeMillis());
        result.put("status", "success");
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/user/{email}")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Map<String, Object>>> getUserByEmail(@PathVariable String email) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.findByUsernameOrEmail(email);
            result.put("userId", user.getUserId());
            result.put("email", user.getEmail());
            result.put("username", user.getUsername());
            result.put("isActive", user.getIsActive());
            result.put("isEmailVerified", user.getIsEmailVerified());
            result.put("lockedUntil", user.getLockedUntil());
            result.put("loginAttempts", user.getLoginAttempts());
            result.put("passwordHash", user.getPasswordHash() != null ? "EXISTS" : "NULL");
            
            // Get roles using direct repository query to avoid lazy loading issues
            try {
                java.util.List<String> roles = userRoleRepository.findByUser_UserId(user.getUserId())
                    .stream()
                    .map(userRole -> userRole.getRole().getRoleName())
                    .collect(java.util.stream.Collectors.toList());
                result.put("roles", roles);
                result.put("rolesCount", roles.size());
            } catch (Exception roleEx) {
                result.put("roles", new java.util.ArrayList<>());
                result.put("rolesCount", 0);
                result.put("rolesError", roleEx.getMessage());
            }
            
            return ResponseEntity.ok(ApiResponse.success(result));
        } catch (Exception e) {
            result.put("error", e.getMessage());
            result.put("stackTrace", e.getClass().getName());
            java.io.StringWriter sw = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(sw));
            result.put("fullStackTrace", sw.toString());
            return ResponseEntity.ok(ApiResponse.success(result));
        }
    }
    
    @PostMapping("/assign-role/{userId}/{roleName}")
    @Transactional
    public ResponseEntity<ApiResponse<String>> assignRole(
            @PathVariable Integer userId, 
            @PathVariable String roleName) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            Role role = roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            
            // Check if user already has this role
            boolean hasRole = user.getUserRoles().stream()
                    .anyMatch(ur -> ur.getRole().getRoleName().equals(roleName));
            
            if (hasRole) {
                return ResponseEntity.ok(ApiResponse.success("User already has role: " + roleName));
            }
            
            // Assign role
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
            
            return ResponseEntity.ok(ApiResponse.success("Role " + roleName + " assigned to user " + user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to assign role: " + e.getMessage()));
        }
    }
    
    @PostMapping("/unlock-user/{userId}")
    @Transactional
    public ResponseEntity<ApiResponse<String>> unlockUser(@PathVariable Integer userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            
            user.setLockedUntil(null);
            user.setLoginAttempts(0);
            userRepository.save(user);
            
            return ResponseEntity.ok(ApiResponse.success("User " + user.getUsername() + " unlocked successfully"));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Failed to unlock user: " + e.getMessage()));
        }
    }
}
