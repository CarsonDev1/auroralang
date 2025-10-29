package vn.edu.fpt.AuroraLang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.response.CourseResponse;
import vn.edu.fpt.AuroraLang.service.CourseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * UC 01: View Home Page
 * UC 81: View Admin Dashboard
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeController {

    private final CourseService courseService;

    @GetMapping("/home")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHomePage() {
        Map<String, Object> homeData = new HashMap<>();

        List<CourseResponse> popularCourses = courseService.getPopularCourses();
        homeData.put("popularCourses", popularCourses);
        homeData.put("message", "Welcome to AuroraLang - Online Language Learning Platform");

        return ResponseEntity.ok(ApiResponse.success(homeData));
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAdminDashboard() {
        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("message", "Admin Dashboard");

        return ResponseEntity.ok(ApiResponse.success(dashboardData));
    }
}
