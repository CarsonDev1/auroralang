package vn.edu.fpt.AuroraLang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.PageResponse;
import vn.edu.fpt.AuroraLang.entity.CourseEnrollment;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.service.EnrollmentService;
import vn.edu.fpt.AuroraLang.service.UserService;

import java.security.Principal;

/**
 * UC 11: Enroll Course/Class
 * UC 16: View My Courses
 * UC 77: View Course Students
 */
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    
    private final EnrollmentService enrollmentService;
    private final UserService userService;
    
    @PostMapping("/courses/{courseId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<String>> enrollCourse(
            @PathVariable Integer courseId,
            Principal principal) {
        User user = userService.findByUsernameOrEmail(principal.getName());
        enrollmentService.enrollCourse(user.getUserId(), courseId);
        return ResponseEntity.ok(ApiResponse.success("Enrolled successfully"));
    }
    
    @GetMapping("/my-courses")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<CourseEnrollment>>> getMyEnrollments(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = userService.findByUsernameOrEmail(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseEnrollment> enrollmentPage = enrollmentService.getUserEnrollments(user.getUserId(), pageable);
        
        PageResponse<CourseEnrollment> pageResponse = new PageResponse<>(
                enrollmentPage.getContent(),
                enrollmentPage.getNumber(),
                enrollmentPage.getSize(),
                enrollmentPage.getTotalElements(),
                enrollmentPage.getTotalPages(),
                enrollmentPage.isLast(),
                enrollmentPage.isFirst()
        );
        
        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }
    
    @GetMapping("/courses/{courseId}/students")
    @PreAuthorize("hasAnyRole('EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<CourseEnrollment>>> getCourseStudents(
            @PathVariable Integer courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseEnrollment> enrollmentPage = enrollmentService.getCourseEnrollments(courseId, pageable);
        
        PageResponse<CourseEnrollment> pageResponse = new PageResponse<>(
                enrollmentPage.getContent(),
                enrollmentPage.getNumber(),
                enrollmentPage.getSize(),
                enrollmentPage.getTotalElements(),
                enrollmentPage.getTotalPages(),
                enrollmentPage.isLast(),
                enrollmentPage.isFirst()
        );
        
        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }
}

