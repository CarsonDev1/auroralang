package vn.edu.fpt.AuroraLang.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Hello from AuroraLang API!");
        result.put("status", "success");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
    
    @PostMapping("/echo")
    public Map<String, Object> echo(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Echo received");
        result.put("received", body);
        result.put("status", "success");
        return result;
    }
}
