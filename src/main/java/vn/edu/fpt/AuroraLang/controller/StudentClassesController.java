package vn.edu.fpt.AuroraLang.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.PageResponse;
import vn.edu.fpt.AuroraLang.dto.response.ClassResponse;
import vn.edu.fpt.AuroraLang.dto.response.SessionResponse;
import vn.edu.fpt.AuroraLang.entity.ClassEnrollment;
import vn.edu.fpt.AuroraLang.entity.ClassSession;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.repository.ClassEnrollmentRepository;
import vn.edu.fpt.AuroraLang.repository.ClassSessionRepository;
import vn.edu.fpt.AuroraLang.service.ClassService;
import vn.edu.fpt.AuroraLang.service.UserService;

@RestController
@RequestMapping("/api/student/classes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('STUDENT','TEACHER','EXPERT','MANAGER','ADMIN')")
public class StudentClassesController {

	private final ClassEnrollmentRepository classEnrollmentRepository;
	private final ClassSessionRepository classSessionRepository;
	private final ClassService classService;
	private final UserService userService;

	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<ClassResponse>>> getMyClasses(
			Principal principal,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		User me = userService.findByUsernameOrEmail(principal.getName());
		Pageable pageable = PageRequest.of(page, size);
		Page<ClassEnrollment> enrollPage = classEnrollmentRepository.findByUser_UserId(me.getUserId(), pageable);
		List<ClassResponse> content = enrollPage.getContent().stream()
				.map(ce -> classService.getById(ce.getClassEntity().getClassId()))
				.collect(Collectors.toList());
		PageResponse<ClassResponse> resp = new PageResponse<>(
				content,
				enrollPage.getNumber(),
				enrollPage.getSize(),
				enrollPage.getTotalElements(),
				enrollPage.getTotalPages(),
				enrollPage.isLast(),
				enrollPage.isFirst());
		return ResponseEntity.ok(ApiResponse.success(resp));
	}

	@GetMapping("/{classId}")
	public ResponseEntity<ApiResponse<ClassResponse>> getClassDetail(
			Principal principal,
			@PathVariable Integer classId) {
		User me = userService.findByUsernameOrEmail(principal.getName());
		boolean enrolled = classEnrollmentRepository.existsByUser_UserIdAndClassEntity_ClassId(me.getUserId(), classId);
		if (!enrolled) {
			return ResponseEntity.status(403).body(ApiResponse.error("You are not enrolled in this class"));
		}
		return ResponseEntity.ok(ApiResponse.success(classService.getById(classId)));
	}

	@GetMapping("/{classId}/sessions")
	public ResponseEntity<ApiResponse<List<SessionResponse>>> getClassSessions(
			Principal principal,
			@PathVariable Integer classId) {
		User me = userService.findByUsernameOrEmail(principal.getName());
		boolean enrolled = classEnrollmentRepository.existsByUser_UserIdAndClassEntity_ClassId(me.getUserId(), classId);
		if (!enrolled) {
			return ResponseEntity.status(403).body(ApiResponse.error("You are not enrolled in this class"));
		}
		List<SessionResponse> sessions = classSessionRepository
				.findByClassEntity_ClassIdOrderBySessionDateAsc(classId)
				.stream()
				.map(this::mapToSession)
				.collect(Collectors.toList());
		return ResponseEntity.ok(ApiResponse.success(sessions));
	}

	private SessionResponse mapToSession(ClassSession s) {
		return new SessionResponse(
				s.getSessionId(),
				s.getSessionNumber(),
				s.getSessionName(),
				s.getSessionDate(),
				s.getStartTime(),
				s.getEndTime()
		);
	}
}


