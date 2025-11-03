package vn.edu.fpt.AuroraLang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerPageController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "manager/dashboard";
    }

    // Classes
    @GetMapping({"/classes", "/classes/", "/classes/class-list"})
    public String classList() {
        return "manager/classes/class-list";
    }

    @GetMapping("/classes/new")
    public String classCreate() {
        return "manager/classes/class-create";
    }

    @GetMapping("/classes/{classId}")
    public String classManage(@PathVariable Integer classId, Model model) {
        model.addAttribute("classId", classId);
        return "manager/classes/class-manage";
    }

    // Students
    @GetMapping({"/students", "/students/", "/students/student-list"})
    public String studentList() {
        return "manager/students/student-list";
    }

    @GetMapping("/students/new")
    public String studentCreate() {
        return "manager/students/student-manage";
    }

    @GetMapping("/students/{studentId}")
    public String studentManage(@PathVariable Integer studentId, Model model) {
        model.addAttribute("studentId", studentId);
        return "manager/students/student-manage";
    }

    // Sessions (Iteration 3)
    @GetMapping({"/sessions", "/sessions/", "/sessions/session-list"})
    public String sessionList(@RequestParam(required = false) Integer classId, Model model) {
        if (classId != null) {
            model.addAttribute("classId", classId);
        }
        return "manager/sessions/session-list";
    }

    @GetMapping("/sessions/new")
    public String sessionCreate(@RequestParam(required = false) Integer classId, Model model) {
        if (classId != null) {
            model.addAttribute("classId", classId);
        }
        return "manager/sessions/session-create";
    }

    @GetMapping("/sessions/{sessionId}")
    public String sessionDetail(@PathVariable Integer sessionId, Model model) {
        model.addAttribute("sessionId", sessionId);
        return "manager/sessions/session-detail";
    }
}


