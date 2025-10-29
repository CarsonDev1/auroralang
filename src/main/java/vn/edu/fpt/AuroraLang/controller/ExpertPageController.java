package vn.edu.fpt.AuroraLang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertPageController {

    // Dashboard
    @GetMapping({"/", "/dashboard"})
    public String dashboard() {
        return "expert/dashboard";
    }

    // Courses
    @GetMapping({"/courses", "/courses/", "/courses/course-list"})
    public String courseList() {
        return "expert/courses/course-list";
    }

    @GetMapping("/courses/new")
    public String courseCreate() {
        return "expert/courses/course-create";
    }

    @GetMapping("/courses/{courseId}")
    public String courseDetail(@PathVariable Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "expert/courses/course-detail";
    }

    @GetMapping("/courses/{courseId}/edit")
    public String courseEdit(@PathVariable Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "expert/courses/course-edit";
    }

    // Course Settings
    @GetMapping({"/courses/settings", "/courses/course-settings"})
    public String courseSettings() {
        return "expert/courses/course-settings";
    }

    @GetMapping("/courses/{courseId}/settings")
    public String courseSettingsDetail(@PathVariable Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "expert/courses/course-settings-detail";
    }

    // Lessons
    @GetMapping({"/lessons", "/lessons/", "/lessons/lesson-list"})
    public String lessonList() {
        return "expert/lessons/lesson-list";
    }

    @GetMapping("/lessons/new")
    public String lessonCreate() {
        return "expert/lessons/lesson-create";
    }

    @GetMapping("/lessons/{lessonId}")
    public String lessonEdit(@PathVariable Integer lessonId, Model model) {
        model.addAttribute("lessonId", lessonId);
        return "expert/lessons/lesson-edit";
    }

    // Flashcards
    @GetMapping({"/flashcards", "/flashcards/", "/flashcards/flashcard-list"})
    public String flashcardList() {
        return "expert/flashcards/flashcard-list";
    }

    @GetMapping("/flashcards/new")
    public String flashcardCreate() {
        return "expert/flashcards/flashcard-create";
    }

    @GetMapping("/flashcards/{flashcardId}")
    public String flashcardEdit(@PathVariable Integer flashcardId, Model model) {
        model.addAttribute("flashcardId", flashcardId);
        return "expert/flashcards/flashcard-edit";
    }

    // Tests
    @GetMapping({"/tests", "/tests/", "/tests/test-list"})
    public String testList() {
        return "expert/tests/test-list";
    }

    @GetMapping("/tests/new")
    public String testCreate() {
        return "expert/tests/test-create";
    }

    @GetMapping("/tests/{testId}")
    public String testEdit(@PathVariable Integer testId, Model model) {
        model.addAttribute("testId", testId);
        return "expert/tests/test-edit";
    }
}
