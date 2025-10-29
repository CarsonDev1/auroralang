package vn.edu.fpt.AuroraLang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserPageController {
  @GetMapping({ "", "/", "/user-list" })
  public String listPage() {
    return "admin/users/user-list";
  }

  @GetMapping("/new")
  public String newUserPage() {
    return "admin/users/user-create";
  }

  @GetMapping("/{userId}")
  public String detailPage(@PathVariable Integer userId, Model model) {
    model.addAttribute("userId", userId);
    return "admin/users/user-edit";
  }
}
