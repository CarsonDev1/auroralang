package vn.edu.fpt.AuroraLang.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {
    
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
    
    @GetMapping("/api/ping")
    public String apiPing() {
        return "API pong";
    }
}
