package vn.edu.fpt.AuroraLang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {
  @GetMapping("/admin/dashboard")
  public String adminDashboardPage(Model model) {
    model.addAttribute("message", "Admin Dashboard");
    return "admin/dashboard";
  }
}
