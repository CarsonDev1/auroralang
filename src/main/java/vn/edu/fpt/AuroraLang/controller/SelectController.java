package vn.edu.fpt.AuroraLang.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.response.SelectOption;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.repository.CourseRepository;
import vn.edu.fpt.AuroraLang.repository.UserRepository;

@RestController
@RequestMapping("/api/select")
@RequiredArgsConstructor
public class SelectController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @GetMapping("/courses")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','EXPERT','TEACHER')")
    public ResponseEntity<ApiResponse<List<SelectOption>>> courses(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(0, Math.min(size, 100));
        var page = q == null || q.isBlank()
                ? courseRepository.findAll(pageable)
                : courseRepository.searchAllCourses(q, pageable);
        List<SelectOption> options = page.getContent().stream()
                .map(c -> new SelectOption(c.getCourseId(), c.getCourseName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(options));
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<List<SelectOption>>> teachers(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(0, Math.min(size, 100));
        var page = (q == null || q.isBlank())
                ? userRepository.findByRoleName("TEACHER", pageable)
                : userRepository.searchUsers(q, pageable); // fallback search
        List<SelectOption> options = page.getContent().stream()
                .filter(u -> hasTeacher(u))
                .map(u -> new SelectOption(u.getUserId(), safeName(u)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(options));
    }

    private boolean hasTeacher(User u) {
        return u.getUserRoles() != null && u.getUserRoles().stream()
                .anyMatch(r -> r.getRole() != null && "TEACHER".equalsIgnoreCase(r.getRole().getRoleName()));
    }

    private String safeName(User u) {
        if (u.getFullName() != null && !u.getFullName().isBlank()) return u.getFullName();
        if (u.getUsername() != null) return u.getUsername();
        return "User " + u.getUserId();
    }

    @GetMapping("/students")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','TEACHER')")
    public ResponseEntity<ApiResponse<List<SelectOption>>> students(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(0, Math.min(size, 100));
        var page = (q == null || q.isBlank())
                ? userRepository.findByRoleName("STUDENT", pageable)
                : userRepository.searchUsers(q, pageable);
        List<SelectOption> options = page.getContent().stream()
                .filter(u -> hasStudent(u))
                .map(u -> new SelectOption(u.getUserId(), safeName(u)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(options));
    }

    private boolean hasStudent(User u) {
        return u.getUserRoles() != null && u.getUserRoles().stream()
                .anyMatch(r -> r.getRole() != null && "STUDENT".equalsIgnoreCase(r.getRole().getRoleName()));
    }
}


