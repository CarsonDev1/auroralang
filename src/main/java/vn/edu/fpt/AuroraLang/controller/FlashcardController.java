package vn.edu.fpt.AuroraLang.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import vn.edu.fpt.AuroraLang.dto.request.FlashcardRequest;
import vn.edu.fpt.AuroraLang.dto.response.FlashcardResponse;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.service.FlashcardService;
import vn.edu.fpt.AuroraLang.service.UserService;

@RestController
@RequestMapping("/api/flashcards")
@RequiredArgsConstructor
public class FlashcardController {

    private final FlashcardService flashcardService;
    private final UserService userService;

    @GetMapping("/my-flashcards")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<FlashcardResponse>>> getMyFlashcards(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = userService.findByUsernameOrEmail(principal.getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<FlashcardResponse> flashcardPage = flashcardService.getUserFlashcards(user.getUserId(), pageable);
        
        PageResponse<FlashcardResponse> pageResponse = new PageResponse<>(
                flashcardPage.getContent(),
                flashcardPage.getNumber(),
                flashcardPage.getSize(),
                flashcardPage.getTotalElements(),
                flashcardPage.getTotalPages(),
                flashcardPage.isLast(),
                flashcardPage.isFirst()
        );
        
        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }

    @GetMapping("/courses/{courseId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<FlashcardResponse>>> getCourseFlashcards(
            @PathVariable Integer courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FlashcardResponse> flashcardPage = flashcardService.getCourseFlashcards(courseId, pageable);
        
        PageResponse<FlashcardResponse> pageResponse = new PageResponse<>(
                flashcardPage.getContent(),
                flashcardPage.getNumber(),
                flashcardPage.getSize(),
                flashcardPage.getTotalElements(),
                flashcardPage.getTotalPages(),
                flashcardPage.isLast(),
                flashcardPage.isFirst()
        );
        
        return ResponseEntity.ok(ApiResponse.success(pageResponse));
    }

    @GetMapping("/{flashcardId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<FlashcardResponse>> getFlashcard(@PathVariable Integer flashcardId) {
        FlashcardResponse flashcard = flashcardService.getById(flashcardId);
        return ResponseEntity.ok(ApiResponse.success(flashcard));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<FlashcardResponse>> createFlashcard(
            @Valid @RequestBody FlashcardRequest request,
            Principal principal) {
        User user = userService.findByUsernameOrEmail(principal.getName());
        FlashcardResponse flashcard = flashcardService.create(user.getUserId(), request);
        return ResponseEntity.ok(ApiResponse.success("Flashcard created successfully", flashcard));
    }

    @PutMapping("/{flashcardId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<FlashcardResponse>> updateFlashcard(
            @PathVariable Integer flashcardId,
            @Valid @RequestBody FlashcardRequest request) {
        FlashcardResponse flashcard = flashcardService.update(flashcardId, request);
        return ResponseEntity.ok(ApiResponse.success("Flashcard updated successfully", flashcard));
    }

    @DeleteMapping("/{flashcardId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'EXPERT', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteFlashcard(@PathVariable Integer flashcardId) {
        flashcardService.delete(flashcardId);
        return ResponseEntity.ok(ApiResponse.success("Flashcard deleted successfully"));
    }
}
