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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.PageResponse;
import vn.edu.fpt.AuroraLang.dto.response.StudentResponse;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.service.ClassEnrollmentService;
import vn.edu.fpt.AuroraLang.service.UserService;

@RestController
@RequestMapping("/api/manager/students")
@RequiredArgsConstructor
public class ManagerStudentController {

    private final ClassEnrollmentService enrollmentService;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<StudentResponse>>> getStudents(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User me = userService.findByUsernameOrEmail(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentResponse> p = enrollmentService.getManagerStudents(me.getUserId(), pageable);
        PageResponse<StudentResponse> resp = new PageResponse<>(
                p.getContent(), p.getNumber(), p.getSize(), 
                p.getTotalElements(), p.getTotalPages(), p.isLast(), p.isFirst());
        return ResponseEntity.ok(ApiResponse.success(resp));
    }

    @PostMapping("/classes/{classId}/students/{studentId}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<ApiResponse<String>> enrollToClass(
            Principal principal,
            @PathVariable Integer classId,
            @PathVariable Integer studentId) {
        User me = userService.findByUsernameOrEmail(principal.getName());
        enrollmentService.enrollStudentToClass(classId, studentId, me.getUserId());
        return ResponseEntity.ok(ApiResponse.success("Student enrolled"));
    }
}

