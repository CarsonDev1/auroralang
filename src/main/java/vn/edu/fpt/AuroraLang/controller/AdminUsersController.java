package vn.edu.fpt.AuroraLang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.PageResponse;
import vn.edu.fpt.AuroraLang.dto.request.UpdateProfileRequest;
import vn.edu.fpt.AuroraLang.dto.request.RegisterRequest;
import vn.edu.fpt.AuroraLang.dto.response.UserResponse;
import vn.edu.fpt.AuroraLang.service.UserService;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

  private final UserService userService;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) String q) {
    Pageable pageable = PageRequest.of(page, size);
    Page<UserResponse> result = (q == null || q.isBlank())
        ? userService.getAllUsers(pageable)
        : userService.searchUsers(q, pageable);
    PageResponse<UserResponse> resp = new PageResponse<>(
        result.getContent(), result.getNumber(), result.getSize(),
        result.getTotalElements(), result.getTotalPages(),
        result.isLast(), result.isFirst());
    return ResponseEntity.ok(ApiResponse.success(resp));
  }

  @GetMapping("/{userId:\\d+}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable("userId") Integer userId) {
    return ResponseEntity.ok(ApiResponse.success(userService.getUserById(userId)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(ApiResponse.success("Created", userService.registerUser(request)));
  }

  @PutMapping("/{userId:\\d+}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable("userId") Integer userId,
      @RequestBody UpdateProfileRequest request) {
    return ResponseEntity.ok(ApiResponse.success("Updated", userService.updateProfile(userId, request)));
  }

  @GetMapping("/roles")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<java.util.List<String>>> getRoles() {
    return ResponseEntity.ok(ApiResponse.success(userService.getAllRoleNames()));
  }

  @GetMapping("/{userId:\\d+}/roles")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<java.util.List<String>>> getUserRoles(@PathVariable("userId") Integer userId) {
    return ResponseEntity.ok(ApiResponse.success(userService.getUserRoleNames(userId)));
  }

  @PutMapping("/{userId:\\d+}/roles")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<String>> setRoles(@PathVariable("userId") Integer userId,
      @RequestBody java.util.List<String> roles) {
    userService.setUserRoles(userId, roles);
    return ResponseEntity.ok(ApiResponse.success("Roles updated"));
  }

  @PatchMapping("/{userId:\\d+}/activate")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<String>> activate(@PathVariable("userId") Integer userId) {
    userService.activateDeactivateUser(userId, true);
    return ResponseEntity.ok(ApiResponse.success("User activated"));
  }

  @PatchMapping("/{userId:\\d+}/deactivate")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<String>> deactivate(@PathVariable("userId") Integer userId) {
    userService.activateDeactivateUser(userId, false);
    return ResponseEntity.ok(ApiResponse.success("User deactivated"));
  }

  @DeleteMapping("/{userId:\\d+}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<String>> delete(@PathVariable("userId") Integer userId) {
    userService.deleteUser(userId);
    return ResponseEntity.ok(ApiResponse.success("User deleted"));
  }
}
