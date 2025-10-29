package vn.edu.fpt.AuroraLang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.request.LessonRequest;
import vn.edu.fpt.AuroraLang.dto.response.LessonResponse;
import vn.edu.fpt.AuroraLang.service.LessonService;

import java.util.List;

/**
 * UC 20: View Class/Course Lesson List
 * UC 21: View Class/Course Lesson Details
 * UC 36: View Course Lesson List
 * UC 37: Delete Course Lesson
 * UC 38: Add New Course Lesson
 * UC 39: Update Course Lesson Details
 */
@RestController
@RequestMapping("/api/courses/{courseId}/lessons")
@RequiredArgsConstructor
public class LessonController {
    
    private final LessonService lessonService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getCourseLessons(@PathVariable Integer courseId) {
        List<LessonResponse> lessons = lessonService.getCourseLessons(courseId);
        return ResponseEntity.ok(ApiResponse.success(lessons));
    }
    
    @GetMapping("/{lessonId}")
    public ResponseEntity<ApiResponse<LessonResponse>> getLessonDetails(@PathVariable Integer lessonId) {
        LessonResponse lesson = lessonService.getLessonById(lessonId);
        return ResponseEntity.ok(ApiResponse.success(lesson));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('EXPERT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<LessonResponse>> createLesson(
            @PathVariable Integer courseId,
            @Valid @RequestBody LessonRequest request) {
        LessonResponse lesson = lessonService.createLesson(courseId, request);
        return ResponseEntity.ok(ApiResponse.success("Lesson created successfully", lesson));
    }
    
    @PutMapping("/{lessonId}")
    @PreAuthorize("hasAnyRole('EXPERT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<LessonResponse>> updateLesson(
            @PathVariable Integer lessonId,
            @Valid @RequestBody LessonRequest request) {
        LessonResponse lesson = lessonService.updateLesson(lessonId, request);
        return ResponseEntity.ok(ApiResponse.success("Lesson updated successfully", lesson));
    }
    
    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAnyRole('EXPERT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteLesson(@PathVariable Integer lessonId) {
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.ok(ApiResponse.success("Lesson deleted successfully"));
    }
}

