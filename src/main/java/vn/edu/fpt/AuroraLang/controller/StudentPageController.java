package vn.edu.fpt.AuroraLang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentPageController {

    @GetMapping({"/", "/dashboard"})
    public String dashboard() { return "student/dashboard"; }

    // Courses
    @GetMapping({"/courses", "/courses/"})
    public String myCourses() { return "student/courses/my-courses"; }

    @GetMapping("/courses/{courseId}")
    public String courseDetail(@PathVariable Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "student/courses/course-detail";
    }

    // Classes
    @GetMapping({"/classes", "/classes/"})
    public String myClasses() { return "student/classes/my-classes"; }

    @GetMapping("/classes/{classId}")
    public String classDetail(@PathVariable Integer classId, Model model) {
        model.addAttribute("classId", classId);
        return "student/classes/class-detail";
    }

    // Lessons
    @GetMapping({"/lessons", "/lessons/"})
    public String lessons() { return "student/lessons/lesson-list"; }

    @GetMapping("/lessons/{lessonId}")
    public String lessonViewer(@PathVariable Integer lessonId, Model model) {
        model.addAttribute("lessonId", lessonId);
        return "student/lessons/lesson-viewer";
    }

    // Tests
    @GetMapping({"/tests", "/tests/"})
    public String tests() { return "student/tests/test-list"; }

    @GetMapping("/tests/{testId}")
    public String testTaking(@PathVariable Integer testId, Model model) {
        model.addAttribute("testId", testId);
        return "student/tests/test-taking";
    }

    @GetMapping("/tests/{testId}/result")
    public String testResult(@PathVariable Integer testId, Model model) {
        model.addAttribute("testId", testId);
        return "student/tests/test-result";
    }

    @GetMapping("/tests/{testId}/review")
    public String testReview(@PathVariable Integer testId, Model model) {
        model.addAttribute("testId", testId);
        return "student/tests/test-review";
    }

    @GetMapping("/tests/{testId}/speaking")
    public String speakingTest(@PathVariable Integer testId, Model model) {
        model.addAttribute("testId", testId);
        return "student/tests/speaking-test";
    }

    // Schedule (Iteration 3)
    @GetMapping("/schedule")
    public String schedule() { return "student/schedule/schedule"; }

    @GetMapping("/classes/{classId}/schedule")
    public String classSchedule(@PathVariable Integer classId, Model model) {
        model.addAttribute("classId", classId);
        return "student/schedule/class-schedule";
    }

    @GetMapping("/sessions/{sessionId}")
    public String sessionDetail(@PathVariable Integer sessionId, Model model) {
        model.addAttribute("sessionId", sessionId);
        return "student/sessions/session-detail";
    }

    // Flashcards library and activities
    @GetMapping("/flashcards")
    public String myFlashcards() { return "student/flashcards/my-flashcards"; }

    @GetMapping("/flashcards/{flashcardId}")
    public String flashcardDetail(@PathVariable Integer flashcardId, Model model) {
        model.addAttribute("flashcardId", flashcardId);
        return "student/flashcards/flashcard-detail";
    }

    @GetMapping("/flashcards/keyword")
    public String keywordReview() { return "student/flashcards/keyword-review"; }

    @GetMapping("/flashcards/matching")
    public String matchingGame() { return "student/flashcards/matching-game"; }

    @GetMapping("/flashcards/reflex")
    public String reflexPractice() { return "student/flashcards/reflex-practice"; }

    @GetMapping("/flashcards/study")
    public String studyMode() { return "student/flashcards/study-mode"; }
}


