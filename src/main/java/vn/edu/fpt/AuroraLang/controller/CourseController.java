package vn.edu.fpt.AuroraLang.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.PageResponse;
import vn.edu.fpt.AuroraLang.dto.request.CourseRequest;
import vn.edu.fpt.AuroraLang.dto.response.CourseResponse;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.service.CourseService;
import vn.edu.fpt.AuroraLang.service.UserService;

/**
 * UC 07: View Public Courses
 * UC 08: View Public Course Details
 * UC 16: View My Courses
 * UC 31: View Expert Course List
 * UC 32: View Course Details
 * UC 75: View Manager Course List
 * UC 76: View Manager Course Details
 * UC 90: View Course List (Admin)
 * UC 91: Delete Course
 * UC 92: Activate/Deactivate Course
 * UC 93: Add New Course
 * UC 94: Update Course Details
 */
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    
    private final CourseService courseService;
    private final UserService userService;
    
    @GetMapping("/public")
    public ResponseEntity<ApiResponse<PageResponse<CourseResponse>>> getPublicCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> coursePage = courseService.getPublicCourses(pageable);
        
        PageResponse<CourseResponse> pageResponse = new PageResponse<>(
                coursePage.getContent(),
                coursePage.getNumber(),
                coursePage.getSize(),
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.isLast(),
                coursePage.isFirst()
        );
        
        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }
    
    @GetMapping("/public/{courseId}")
    public ResponseEntity<ApiResponse<CourseResponse>> getPublicCourseDetails(@PathVariable Integer courseId) {
        CourseResponse course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(ApiResponse.success(course));
    }
    
    @GetMapping("/public/popular")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getPopularCourses() {
        List<CourseResponse> courses = courseService.getPopularCourses();
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<CourseResponse>>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> coursePage = (q == null || q.isBlank())
                ? courseService.getAllCourses(pageable)
                : courseService.searchAllCourses(q, pageable);

        PageResponse<CourseResponse> pageResponse = new PageResponse<>(
                coursePage.getContent(),
                coursePage.getNumber(),
                coursePage.getSize(),
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.isLast(),
                coursePage.isFirst()
        );

        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }
    
    @GetMapping("/public/search")
    public ResponseEntity<ApiResponse<PageResponse<CourseResponse>>> searchPublicCourses(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> coursePage = courseService.searchPublicCourses(keyword, pageable);
        
        PageResponse<CourseResponse> pageResponse = new PageResponse<>(
                coursePage.getContent(),
                coursePage.getNumber(),
                coursePage.getSize(),
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.isLast(),
                coursePage.isFirst()
        );
        
        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }
    
    @GetMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseDetails(@PathVariable Integer courseId) {
        CourseResponse course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(ApiResponse.success(course));
    }
    
    @GetMapping("/expert/my-courses")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<CourseResponse>>> getExpertCourses(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = userService.findByUsernameOrEmail(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseResponse> coursePage = courseService.getExpertCourses(user.getUserId(), pageable);
        
        PageResponse<CourseResponse> pageResponse = new PageResponse<>(
                coursePage.getContent(),
                coursePage.getNumber(),
                coursePage.getSize(),
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.isLast(),
                coursePage.isFirst()
        );
        
        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @Valid @RequestBody CourseRequest request,
            Principal principal) {
        User user = userService.findByUsernameOrEmail(principal.getName());
        CourseResponse course = courseService.createCourse(user.getUserId(), request);
        return ResponseEntity.ok(ApiResponse.success("Course created successfully", course));
    }
    
    @PutMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @PathVariable Integer courseId,
            @Valid @RequestBody CourseRequest request) {
        CourseResponse course = courseService.updateCourse(courseId, request);
        return ResponseEntity.ok(ApiResponse.success("Course updated successfully", course));
    }
    
    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success("Course deleted successfully"));
    }
    
    @PatchMapping("/{courseId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> activateCourse(@PathVariable Integer courseId) {
        courseService.activateDeactivateCourse(courseId, true);
        return ResponseEntity.ok(ApiResponse.success("Course activated successfully"));
    }
    
    @PatchMapping("/{courseId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deactivateCourse(@PathVariable Integer courseId) {
        courseService.activateDeactivateCourse(courseId, false);
        return ResponseEntity.ok(ApiResponse.success("Course deactivated successfully"));
    }
}

