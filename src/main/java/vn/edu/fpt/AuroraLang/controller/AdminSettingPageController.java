package vn.edu.fpt.AuroraLang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/settings")
public class AdminSettingPageController {
  @GetMapping("/system-settings")
  public String systemSettingsPage() {
    return "admin/settings/system-settings";
  }

  @GetMapping("/{settingId}")
  public String systemSettingDetailPage(@PathVariable Integer settingId, Model model) {
    model.addAttribute("settingId", settingId);
    return "admin/settings/setting-detail";
  }
}
