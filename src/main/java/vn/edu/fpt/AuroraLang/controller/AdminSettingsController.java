package vn.edu.fpt.AuroraLang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.AuroraLang.dto.ApiResponse;
import vn.edu.fpt.AuroraLang.dto.request.SystemSettingRequest;
import vn.edu.fpt.AuroraLang.dto.response.SystemSettingResponse;
import vn.edu.fpt.AuroraLang.service.SystemSettingService;

import java.util.List;

/**
 * UC 82: View Setting List (Admin)
 * UC 83: Activate/Deactivate Setting (Admin)
 * UC 84: Add New Setting (Admin)
 * UC 85: Update Setting Details (Admin)
 */
@RestController
@RequestMapping("/api/admin/settings")
@RequiredArgsConstructor
public class AdminSettingsController {
    
    private final SystemSettingService systemSettingService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<SystemSettingResponse>>> getSettings() {
        List<SystemSettingResponse> settings = systemSettingService.getAllSettings();
        return ResponseEntity.ok(ApiResponse.success(settings));
    }
    
    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<SystemSettingResponse>>> getActiveSettings() {
        List<SystemSettingResponse> settings = systemSettingService.getActiveSettings();
        return ResponseEntity.ok(ApiResponse.success(settings));
    }
    
    @GetMapping("/{settingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SystemSettingResponse>> getSettingDetails(@PathVariable Integer settingId) {
        SystemSettingResponse setting = systemSettingService.getSettingById(settingId);
        return ResponseEntity.ok(ApiResponse.success(setting));
    }
    
    @GetMapping("/key/{settingKey}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SystemSettingResponse>> getSettingByKey(@PathVariable String settingKey) {
        SystemSettingResponse setting = systemSettingService.getSettingByKey(settingKey);
        return ResponseEntity.ok(ApiResponse.success(setting));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SystemSettingResponse>> createSetting(@Valid @RequestBody SystemSettingRequest request) {
        SystemSettingResponse setting = systemSettingService.createSetting(request);
        return ResponseEntity.ok(ApiResponse.success("Setting created successfully", setting));
    }
    
    @PutMapping("/{settingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SystemSettingResponse>> updateSetting(
            @PathVariable Integer settingId,
            @Valid @RequestBody SystemSettingRequest request) {
        SystemSettingResponse setting = systemSettingService.updateSetting(settingId, request);
        return ResponseEntity.ok(ApiResponse.success("Setting updated successfully", setting));
    }
    
    @DeleteMapping("/{settingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteSetting(@PathVariable Integer settingId) {
        systemSettingService.deleteSetting(settingId);
        return ResponseEntity.ok(ApiResponse.success("Setting deleted successfully"));
    }
    
    @PatchMapping("/{settingId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> activateSetting(@PathVariable Integer settingId) {
        systemSettingService.activateDeactivateSetting(settingId, true);
        return ResponseEntity.ok(ApiResponse.success("Setting activated successfully"));
    }
    
    @PatchMapping("/{settingId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deactivateSetting(@PathVariable Integer settingId) {
        systemSettingService.activateDeactivateSetting(settingId, false);
        return ResponseEntity.ok(ApiResponse.success("Setting deactivated successfully"));
    }
}
