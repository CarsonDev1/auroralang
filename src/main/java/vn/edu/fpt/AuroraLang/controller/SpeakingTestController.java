package vn.edu.fpt.AuroraLang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.response.SpeakingTestAttemptResponse;
import vn.edu.fpt.AuroraLang.dto.response.SpeakingTestQuestionResponse;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.service.SpeakingTestService;
import vn.edu.fpt.AuroraLang.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.io.IOException;

@RestController
@RequestMapping("/api/student/speaking-tests")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('STUDENT','TEACHER','EXPERT','MANAGER','ADMIN')")
public class SpeakingTestController {

    private final SpeakingTestService speakingTestService;
    private final UserService userService;

    @GetMapping("/{testId}/questions")
    public ResponseEntity<ApiResponse<List<SpeakingTestQuestionResponse>>> getQuestions(@PathVariable Integer testId) {
        List<SpeakingTestQuestionResponse> questions = speakingTestService.getSpeakingQuestions(testId);
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    @PostMapping("/{testId}/attempts")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createAttempt(
            Principal principal,
            @PathVariable Integer testId) {
        User user = userService.findByUsernameOrEmail(principal.getName());
        var attempt = speakingTestService.createAttempt(user.getUserId(), testId);
        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "attemptId", attempt.getAttemptId(),
                "startTime", attempt.getStartTime()
        )));
    }

    @PostMapping("/attempts/{attemptId}/answers")
    public ResponseEntity<ApiResponse<Map<String, Object>>> saveAnswer(
            @PathVariable Integer attemptId,
            @RequestParam Integer questionId,
            @RequestParam("audio") MultipartFile audioFile,
            @RequestParam(required = false, defaultValue = "0") Integer durationSeconds) {
        try {
            String audioUrl = speakingTestService.saveAudioFile(audioFile);
            var answer = speakingTestService.saveAnswer(attemptId, questionId, audioUrl, durationSeconds);
            return ResponseEntity.ok(ApiResponse.success(Map.of(
                    "answerId", answer.getAnswerId(),
                    "audioUrl", answer.getAudioUrl(),
                    "score", answer.getScore(),
                    "transcribedText", answer.getTranscribedText()
            )));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to save audio file: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to save answer: " + e.getMessage()));
        }
    }

    @PostMapping("/attempts/{attemptId}/submit")
    public ResponseEntity<ApiResponse<SpeakingTestAttemptResponse>> submitAttempt(
            @PathVariable Integer attemptId) {
        speakingTestService.submitAttempt(attemptId);
        SpeakingTestAttemptResponse response = speakingTestService.getAttempt(attemptId);
        return ResponseEntity.ok(ApiResponse.success("Test submitted successfully", response));
    }

    @GetMapping("/attempts/{attemptId}")
    public ResponseEntity<ApiResponse<SpeakingTestAttemptResponse>> getAttempt(@PathVariable Integer attemptId) {
        SpeakingTestAttemptResponse response = speakingTestService.getAttempt(attemptId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

