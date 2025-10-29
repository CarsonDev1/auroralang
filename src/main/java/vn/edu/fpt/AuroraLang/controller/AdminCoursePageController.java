package vn.edu.fpt.AuroraLang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/courses")
public class AdminCoursePageController {

    @GetMapping({"", "/", "/course-list"})
    public String listPage() {
        return "admin/courses/course-list";
    }

    @GetMapping("/new")
    public String newCoursePage(Model model) {
        model.addAttribute("mode", "create");
        return "admin/courses/course-manage";
    }

    @GetMapping("/{courseId}")
    public String editCoursePage(@PathVariable Integer courseId, Model model) {
        model.addAttribute("mode", "edit");
        model.addAttribute("courseId", courseId);
        return "admin/courses/course-manage";
    }
}


