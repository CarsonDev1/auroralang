package vn.edu.fpt.AuroraLang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.request.CourseSettingRequest;
import vn.edu.fpt.AuroraLang.dto.response.CourseSettingResponse;
import vn.edu.fpt.AuroraLang.service.CourseSettingService;

import java.util.List;

/**
 * UC 33: View Course Settings (Expert)
 * UC 34: Add New Course Setting (Expert)
 * UC 35: Update Course Setting Details (Expert)
 */
@RestController
@RequestMapping("/api/courses/{courseId}/settings")
@RequiredArgsConstructor
public class CourseSettingsController {
    
    private final CourseSettingService courseSettingService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<CourseSettingResponse>>> getCourseSettings(@PathVariable Integer courseId) {
        List<CourseSettingResponse> settings = courseSettingService.getCourseSettings(courseId);
        return ResponseEntity.ok(ApiResponse.success(settings));
    }
    
    @GetMapping("/{settingId}")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ApiResponse<CourseSettingResponse>> getCourseSettingDetails(
            @PathVariable Integer courseId,
            @PathVariable Integer settingId) {
        CourseSettingResponse setting = courseSettingService.getCourseSettingById(courseId, settingId);
        return ResponseEntity.ok(ApiResponse.success(setting));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ApiResponse<CourseSettingResponse>> createCourseSetting(
            @PathVariable Integer courseId,
            @Valid @RequestBody CourseSettingRequest request) {
        CourseSettingResponse setting = courseSettingService.createCourseSetting(courseId, request);
        return ResponseEntity.ok(ApiResponse.success("Course setting created successfully", setting));
    }
    
    @PutMapping("/{settingId}")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ApiResponse<CourseSettingResponse>> updateCourseSetting(
            @PathVariable Integer courseId,
            @PathVariable Integer settingId,
            @Valid @RequestBody CourseSettingRequest request) {
        CourseSettingResponse setting = courseSettingService.updateCourseSetting(courseId, settingId, request);
        return ResponseEntity.ok(ApiResponse.success("Course setting updated successfully", setting));
    }
    
    @DeleteMapping("/{settingId}")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteCourseSetting(
            @PathVariable Integer courseId,
            @PathVariable Integer settingId) {
        courseSettingService.deleteCourseSetting(courseId, settingId);
        return ResponseEntity.ok(ApiResponse.success("Course setting deleted successfully"));
    }
}
