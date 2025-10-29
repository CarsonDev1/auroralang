package vn.edu.fpt.AuroraLang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GuestPageController {

    // Home
    @GetMapping({"/", "/home"})
    public String home() {
        return "guest/home";
    }

    // About
    @GetMapping("/about")
    public String about() {
        return "guest/about";
    }

    // Contact
    @GetMapping("/contact")
    public String contact() {
        return "guest/contact";
    }

    // Courses (public)
    @GetMapping({"/courses", "/courses/", "/courses/course-list"})
    public String courseList() {
        return "course/course-list";
    }

    @GetMapping("/courses/{courseId}")
    public String courseDetail(@PathVariable Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "course/course-detail";
    }

    @GetMapping("/courses/{courseId}/enroll")
    public String courseEnroll(@PathVariable Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "course/course-enroll";
    }

    @GetMapping("/courses/{courseId}/payment")
    public String coursePayment(@PathVariable Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "course/course-payment";
    }
}
