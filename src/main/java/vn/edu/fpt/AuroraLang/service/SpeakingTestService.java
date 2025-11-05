package vn.edu.fpt.AuroraLang.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.edu.fpt.AuroraLang.entity.SpeakingTestAnswer;
import vn.edu.fpt.AuroraLang.entity.SpeakingTestAttempt;
import vn.edu.fpt.AuroraLang.entity.SpeakingTestQuestion;
import vn.edu.fpt.AuroraLang.entity.Test;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.repository.SpeakingTestAnswerRepository;
import vn.edu.fpt.AuroraLang.repository.SpeakingTestAttemptRepository;
import vn.edu.fpt.AuroraLang.repository.SpeakingTestQuestionRepository;
import vn.edu.fpt.AuroraLang.repository.TestRepository;
import vn.edu.fpt.AuroraLang.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpeakingTestService {

    private final SpeakingTestQuestionRepository questionRepository;
    private final SpeakingTestAttemptRepository attemptRepository;
    private final SpeakingTestAnswerRepository answerRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public List<vn.edu.fpt.AuroraLang.dto.response.SpeakingTestQuestionResponse> getSpeakingQuestions(Integer testId) {
        return questionRepository.findByTest_TestIdAndIsActiveTrueOrderByQuestionOrderAsc(testId)
                .stream()
                .map(this::mapToQuestionResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SpeakingTestAttempt createAttempt(Integer userId, Integer testId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        Integer nextAttempt = attemptRepository.findMaxAttemptNumber(userId, testId) + 1;

        SpeakingTestAttempt attempt = new SpeakingTestAttempt();
        attempt.setUser(user);
        attempt.setTest(test);
        attempt.setAttemptNumber(nextAttempt);
        attempt.setStatus(SpeakingTestAttempt.Status.IN_PROGRESS);
        return attemptRepository.save(attempt);
    }

    public String saveAudioFile(MultipartFile audioFile) throws IOException {
        String originalFilename = audioFile.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".webm";
        String filename = UUID.randomUUID().toString() + extension;
        
        Path uploadPath = Paths.get(uploadDir, "speaking");
        Files.createDirectories(uploadPath);
        
        Path filePath = uploadPath.resolve(filename);
        Files.write(filePath, audioFile.getBytes());
        
        return "/uploads/speaking/" + filename;
    }

    @Transactional
    public SpeakingTestAnswer saveAnswer(Integer attemptId, Integer questionId, String audioUrl, Integer durationSeconds) {
        SpeakingTestAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));
        SpeakingTestQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        SpeakingTestAnswer answer = new SpeakingTestAnswer();
        answer.setAttempt(attempt);
        answer.setQuestion(question);
        answer.setAudioUrl(audioUrl);
        answer.setDurationSeconds(durationSeconds);
        
        // TODO: Integrate with speech recognition API (e.g., OpenAI Whisper, Google Speech-to-Text)
        // For now, throw exception to indicate this feature is not yet implemented
        // In production, implement:
        // 1. Call speech recognition API to transcribe audio
        // 2. Evaluate pronunciation accuracy and fluency
        // 3. Calculate score based on evaluation
        // 4. Generate feedback based on analysis
        throw new UnsupportedOperationException(
            "Speaking test evaluation is not yet implemented. " +
            "Please integrate with a speech recognition API (e.g., OpenAI Whisper) to evaluate audio submissions."
        );
    }

    @Transactional
    public SpeakingTestAttempt submitAttempt(Integer attemptId) {
        SpeakingTestAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        List<SpeakingTestAnswer> answers = answerRepository.findByAttempt_AttemptIdOrderByCreatedAtAsc(attemptId);
        
        BigDecimal totalScore = answers.stream()
                .map(a -> a.getScore() != null ? a.getScore() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Test test = attempt.getTest();
        BigDecimal totalPoints = test.getTotalPoints();
        BigDecimal percentage = totalPoints.compareTo(BigDecimal.ZERO) > 0
                ? totalScore.divide(totalPoints, 4, java.math.RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;

        attempt.setSubmitTime(LocalDateTime.now());
        attempt.setScore(totalScore);
        attempt.setPercentage(percentage);
        attempt.setStatus(SpeakingTestAttempt.Status.SUBMITTED);
        attempt.setIsPassed(percentage.compareTo(test.getPassingScore()) >= 0);

        if (attempt.getStartTime() != null) {
            long seconds = java.time.Duration.between(attempt.getStartTime(), LocalDateTime.now()).getSeconds();
            attempt.setTimeSpentSeconds((int) seconds);
        }

        return attemptRepository.save(attempt);
    }

    public vn.edu.fpt.AuroraLang.dto.response.SpeakingTestAttemptResponse getAttempt(Integer attemptId) {
        SpeakingTestAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        List<SpeakingTestAnswer> answers = answerRepository.findByAttempt_AttemptIdOrderByCreatedAtAsc(attemptId);
        
        return vn.edu.fpt.AuroraLang.dto.response.SpeakingTestAttemptResponse.builder()
                .attemptId(attempt.getAttemptId())
                .testId(attempt.getTest().getTestId())
                .testName(attempt.getTest().getTestName())
                .attemptNumber(attempt.getAttemptNumber())
                .startTime(attempt.getStartTime())
                .submitTime(attempt.getSubmitTime())
                .timeSpentSeconds(attempt.getTimeSpentSeconds())
                .score(attempt.getScore())
                .percentage(attempt.getPercentage())
                .status(attempt.getStatus().name())
                .isPassed(attempt.getIsPassed())
                .answers(answers.stream().map(this::mapToAnswerResponse).collect(Collectors.toList()))
                .build();
    }

    private vn.edu.fpt.AuroraLang.dto.response.SpeakingTestQuestionResponse mapToQuestionResponse(SpeakingTestQuestion q) {
        return vn.edu.fpt.AuroraLang.dto.response.SpeakingTestQuestionResponse.builder()
                .questionId(q.getQuestionId())
                .testId(q.getTest().getTestId())
                .questionText(q.getQuestionText())
                .expectedAnswer(q.getExpectedAnswer())
                .audioUrl(q.getAudioUrl())
                .timeLimitSeconds(q.getTimeLimitSeconds())
                .questionOrder(q.getQuestionOrder())
                .points(q.getPoints())
                .createdAt(q.getCreatedAt())
                .build();
    }

    private vn.edu.fpt.AuroraLang.dto.response.SpeakingTestAnswerResponse mapToAnswerResponse(SpeakingTestAnswer a) {
        return vn.edu.fpt.AuroraLang.dto.response.SpeakingTestAnswerResponse.builder()
                .answerId(a.getAnswerId())
                .questionId(a.getQuestion().getQuestionId())
                .questionText(a.getQuestion().getQuestionText())
                .audioUrl(a.getAudioUrl())
                .transcribedText(a.getTranscribedText())
                .score(a.getScore())
                .accuracyScore(a.getAccuracyScore())
                .fluencyScore(a.getFluencyScore())
                .feedback(a.getFeedback())
                .durationSeconds(a.getDurationSeconds())
                .build();
    }
}

