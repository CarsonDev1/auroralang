package vn.edu.fpt.AuroraLang.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.entity.Class;
import vn.edu.fpt.AuroraLang.entity.LessonProgress;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.entity.UserFlashcardProgress;
import vn.edu.fpt.AuroraLang.repository.ClassRepository;
import vn.edu.fpt.AuroraLang.repository.ClassSessionRepository;
import vn.edu.fpt.AuroraLang.repository.FlashcardRepository;
import vn.edu.fpt.AuroraLang.repository.LessonProgressRepository;
import vn.edu.fpt.AuroraLang.repository.LessonRepository;
import vn.edu.fpt.AuroraLang.repository.UserFlashcardProgressRepository;
import vn.edu.fpt.AuroraLang.service.UserService;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final FlashcardRepository flashcardRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;
    private final UserFlashcardProgressRepository userFlashcardProgressRepository;
    private final ClassRepository classRepository;
    private final ClassSessionRepository classSessionRepository;

    @GetMapping("/teacher/statistics")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTeacherStatistics(Principal principal) {
        User teacher = userService.findByUsernameOrEmail(principal.getName());
        
        // Count flashcards created by teacher
        Long totalFlashcards = flashcardRepository.countByUser_UserId(teacher.getUserId());
        
        // Count lessons from courses where teacher teaches classes
        List<Class> teacherClasses = classRepository.findByTeacher_UserId(teacher.getUserId(), 
            org.springframework.data.domain.PageRequest.of(0, 1000)).getContent();
        Set<Integer> courseIds = teacherClasses.stream()
            .map(c -> c.getCourse().getCourseId())
            .collect(Collectors.toSet());
        
        Long totalLessons = courseIds.stream()
            .mapToLong(courseId -> lessonRepository.countByCourse_CourseId(courseId))
            .sum();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalFlashcards", totalFlashcards);
        stats.put("totalLessons", totalLessons);
        
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/student/progress")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStudentProgress(
            Principal principal,
            @RequestParam(defaultValue = "6") int weeks) {
        User student = userService.findByUsernameOrEmail(principal.getName());
        
        // Get progress data for the last N weeks
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusWeeks(weeks - 1);
        
        List<Map<String, Object>> weeklyData = new ArrayList<>();
        
        for (int i = 0; i < weeks; i++) {
            LocalDate weekStart = startDate.plusWeeks(i);
            LocalDate weekEnd = weekStart.plusDays(6);
            
            // Count completed lessons in this week
            List<LessonProgress> allProgress = lessonProgressRepository.findByUser_UserId(student.getUserId());
            Long lessonsCompleted = allProgress.stream()
                .filter(lp -> lp.getStatus() == LessonProgress.Status.COMPLETED)
                .filter(lp -> lp.getCompletedAt() != null)
                .filter(lp -> {
                    LocalDate completedDate = lp.getCompletedAt().toLocalDate();
                    return !completedDate.isBefore(weekStart) && !completedDate.isAfter(weekEnd);
                })
                .count();
            
            // Count flashcards studied in this week
            List<UserFlashcardProgress> allFlashcardProgress = userFlashcardProgressRepository.findByUser_UserId(student.getUserId());
            Long flashcardsStudied = allFlashcardProgress.stream()
                .filter(ufp -> ufp.getLastReviewedAt() != null)
                .filter(ufp -> {
                    LocalDate studiedDate = ufp.getLastReviewedAt().toLocalDate();
                    return !studiedDate.isBefore(weekStart) && !studiedDate.isAfter(weekEnd);
                })
                .count();
            
            Map<String, Object> weekData = new HashMap<>();
            weekData.put("week", "Week " + (i + 1));
            weekData.put("lessonsCompleted", lessonsCompleted.intValue());
            weekData.put("flashcardsStudied", flashcardsStudied.intValue());
            weeklyData.add(weekData);
        }
        
        Map<String, Object> progressData = new HashMap<>();
        progressData.put("labels", weeklyData.stream().map(w -> w.get("week")).collect(Collectors.toList()));
        progressData.put("lessonsCompleted", weeklyData.stream().map(w -> w.get("lessonsCompleted")).collect(Collectors.toList()));
        progressData.put("flashcardsStudied", weeklyData.stream().map(w -> w.get("flashcardsStudied")).collect(Collectors.toList()));
        
        return ResponseEntity.ok(ApiResponse.success(progressData));
    }

    @GetMapping("/schedule/session/{sessionId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSessionDetails(
            @PathVariable Integer sessionId,
            Principal principal) {
        
        return classSessionRepository.findById(sessionId)
            .map(session -> {
                Map<String, Object> details = new HashMap<>();
                details.put("sessionId", session.getSessionId());
                details.put("sessionName", session.getSessionName());
                details.put("time", session.getStartTime() + " - " + session.getEndTime());
                details.put("room", session.getLocation() != null ? session.getLocation() : "TBD");
                details.put("teacher", session.getClassEntity().getTeacher() != null 
                    ? session.getClassEntity().getTeacher().getFullName() : "TBD");
                details.put("description", session.getDescription() != null 
                    ? session.getDescription() : "No description available");
                details.put("date", session.getSessionDate().toString());
                details.put("onlineMeetingUrl", session.getOnlineMeetingUrl());
                
                return ResponseEntity.ok(ApiResponse.success(details));
            })
            .orElse(ResponseEntity.status(404).body(ApiResponse.error("Session not found")));
    }

    @GetMapping("/lesson/{lessonId}/progress")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLessonProgress(
            @PathVariable Integer lessonId,
            Principal principal) {
        User student = userService.findByUsernameOrEmail(principal.getName());
        
        return lessonProgressRepository.findByUser_UserIdAndLesson_LessonId(student.getUserId(), lessonId)
            .map(progress -> {
                Map<String, Object> progressData = new HashMap<>();
                progressData.put("progressPercentage", progress.getProgressPercentage());
                progressData.put("status", progress.getStatus().name());
                progressData.put("timeSpentSeconds", progress.getTimeSpentSeconds());
                progressData.put("startedAt", progress.getStartedAt());
                progressData.put("completedAt", progress.getCompletedAt());
                
                return ResponseEntity.ok(ApiResponse.success(progressData));
            })
            .orElse(ResponseEntity.ok(ApiResponse.success(Map.of(
                "progressPercentage", BigDecimal.ZERO,
                "status", "NOT_STARTED",
                "timeSpentSeconds", 0
            ))));
    }
}

