package vn.edu.fpt.AuroraLang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherPageController {

    // Dashboard
    @GetMapping({"/", "/dashboard"})
    public String dashboard() {
        return "teacher/dashboard";
    }

    // Classes
    @GetMapping({"/classes", "/classes/", "/classes/class-list"})
    public String classList() {
        return "teacher/classes/class-list";
    }

    @GetMapping("/classes/{classId}")
    public String classDetail(@PathVariable Integer classId, Model model) {
        model.addAttribute("classId", classId);
        return "teacher/classes/class-detail";
    }

    @GetMapping("/classes/{classId}/edit")
    public String classEdit(@PathVariable Integer classId, Model model) {
        model.addAttribute("classId", classId);
        return "teacher/classes/class-edit";
    }

    @GetMapping("/classes/{classId}/students")
    public String classStudents(@PathVariable Integer classId, Model model) {
        model.addAttribute("classId", classId);
        return "teacher/classes/class-students";
    }

    @GetMapping("/classes/{classId}/lessons")
    public String classLessons(@PathVariable Integer classId, Model model) {
        model.addAttribute("classId", classId);
        return "teacher/lessons/lesson-list";
    }

    @GetMapping("/classes/{classId}/tests")
    public String classTests(@PathVariable Integer classId, Model model) {
        model.addAttribute("classId", classId);
        return "teacher/tests/test-list";
    }

    // Flashcards
    @GetMapping({"/flashcards", "/flashcards/", "/flashcards/flashcard-list"})
    public String flashcardList() {
        return "teacher/flashcards/flashcard-list";
    }

    @GetMapping("/flashcards/new")
    public String flashcardCreate() {
        return "teacher/flashcards/flashcard-create";
    }

    @GetMapping("/flashcards/{flashcardId}")
    public String flashcardEdit(@PathVariable Integer flashcardId, Model model) {
        model.addAttribute("flashcardId", flashcardId);
        return "teacher/flashcards/flashcard-edit";
    }

    // Lessons
    @GetMapping({"/lessons", "/lessons/", "/lessons/lesson-list"})
    public String lessonList() {
        return "teacher/lessons/lesson-list";
    }

    @GetMapping("/lessons/new")
    public String lessonCreate() {
        return "teacher/lessons/lesson-create";
    }

    @GetMapping("/lessons/{lessonId}")
    public String lessonEdit(@PathVariable Integer lessonId, Model model) {
        model.addAttribute("lessonId", lessonId);
        return "teacher/lessons/lesson-edit";
    }

    // Tests
    @GetMapping({"/tests", "/tests/", "/tests/test-list"})
    public String testList() {
        return "teacher/tests/test-list";
    }

    @GetMapping("/tests/new")
    public String testCreate() {
        return "teacher/tests/test-create";
    }

    @GetMapping("/tests/{testId}")
    public String testEdit(@PathVariable Integer testId, Model model) {
        model.addAttribute("testId", testId);
        return "teacher/tests/test-edit";
    }
}


