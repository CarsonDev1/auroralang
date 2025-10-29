package vn.edu.fpt.AuroraLang.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.PageResponse;
import vn.edu.fpt.AuroraLang.dto.request.ClassRequest;
import vn.edu.fpt.AuroraLang.dto.response.ClassResponse;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.service.ClassService;
import vn.edu.fpt.AuroraLang.service.UserService;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;
    private final UserService userService;

    @GetMapping("/manager/my-classes")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<ClassResponse>>> getManagerClasses(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q) {
        User me = userService.findByUsernameOrEmail(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<ClassResponse> p = (q == null || q.isBlank())
                ? classService.getManagerClasses(me.getUserId(), pageable)
                : classService.searchManagerClasses(me.getUserId(), q, pageable);
        PageResponse<ClassResponse> resp = new PageResponse<>(
                p.getContent(), p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages(), p.isLast(), p.isFirst());
        return ResponseEntity.ok(ApiResponse.success(resp));
    }

    @GetMapping("/teacher/my-classes")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<ClassResponse>>> getTeacherClasses(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q) {
        User me = userService.findByUsernameOrEmail(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<ClassResponse> p = (q == null || q.isBlank())
                ? classService.getTeacherClasses(me.getUserId(), pageable)
                : classService.searchTeacherClasses(me.getUserId(), q, pageable);
        PageResponse<ClassResponse> resp = new PageResponse<>(
                p.getContent(), p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages(), p.isLast(), p.isFirst());
        return ResponseEntity.ok(ApiResponse.success(resp));
    }

    @GetMapping("/{classId}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN','TEACHER')")
    public ResponseEntity<ApiResponse<ClassResponse>> getClass(@PathVariable Integer classId) {
        ClassResponse classResponse = classService.getById(classId);
        return ResponseEntity.ok(ApiResponse.success(classResponse));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<ClassResponse>> create(
            Principal principal,
            @Valid @RequestBody ClassRequest request) {
        User me = userService.findByUsernameOrEmail(principal.getName());
        ClassResponse created = classService.create(me.getUserId(), request);
        return ResponseEntity.ok(ApiResponse.success("Class created", created));
    }

    @PutMapping("/{classId}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<ClassResponse>> update(
            @PathVariable Integer classId,
            @RequestBody ClassRequest request) {
        ClassResponse updated = classService.update(classId, request);
        return ResponseEntity.ok(ApiResponse.success("Class updated", updated));
    }
}


