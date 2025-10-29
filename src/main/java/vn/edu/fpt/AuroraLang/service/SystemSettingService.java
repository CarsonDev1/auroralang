package vn.edu.fpt.AuroraLang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.AuroraLang.dto.request.SystemSettingRequest;
import vn.edu.fpt.AuroraLang.dto.response.SystemSettingResponse;
import vn.edu.fpt.AuroraLang.entity.SystemSetting;
import vn.edu.fpt.AuroraLang.repository.SystemSettingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemSettingService {
    
    private final SystemSettingRepository systemSettingRepository;
    
    public List<SystemSettingResponse> getAllSettings() {
        return systemSettingRepository.findAll().stream()
                .map(this::mapToSystemSettingResponse)
                .collect(Collectors.toList());
    }
    
    public List<SystemSettingResponse> getActiveSettings() {
        return systemSettingRepository.findByIsActive(true).stream()
                .map(this::mapToSystemSettingResponse)
                .collect(Collectors.toList());
    }
    
    public SystemSettingResponse getSettingById(Integer settingId) {
        SystemSetting setting = systemSettingRepository.findById(settingId)
                .orElseThrow(() -> new RuntimeException("Setting not found"));
        return mapToSystemSettingResponse(setting);
    }
    
    public SystemSettingResponse getSettingByKey(String settingKey) {
        SystemSetting setting = systemSettingRepository.findBySettingKey(settingKey)
                .orElseThrow(() -> new RuntimeException("Setting not found"));
        return mapToSystemSettingResponse(setting);
    }
    
    @Transactional
    public SystemSettingResponse createSetting(SystemSettingRequest request) {
        if (systemSettingRepository.findBySettingKey(request.getSettingKey()).isPresent()) {
            throw new RuntimeException("Setting key already exists");
        }
        
        SystemSetting setting = new SystemSetting();
        setting.setSettingKey(request.getSettingKey());
        setting.setSettingValue(request.getSettingValue());
        setting.setSettingType(request.getSettingType());
        setting.setDescription(request.getDescription());
        setting.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        
        SystemSetting savedSetting = systemSettingRepository.save(setting);
        return mapToSystemSettingResponse(savedSetting);
    }
    
    @Transactional
    public SystemSettingResponse updateSetting(Integer settingId, SystemSettingRequest request) {
        SystemSetting setting = systemSettingRepository.findById(settingId)
                .orElseThrow(() -> new RuntimeException("Setting not found"));
        
        if (request.getSettingValue() != null) setting.setSettingValue(request.getSettingValue());
        if (request.getSettingType() != null) setting.setSettingType(request.getSettingType());
        if (request.getDescription() != null) setting.setDescription(request.getDescription());
        if (request.getIsActive() != null) setting.setIsActive(request.getIsActive());
        
        SystemSetting updatedSetting = systemSettingRepository.save(setting);
        return mapToSystemSettingResponse(updatedSetting);
    }
    
    @Transactional
    public void deleteSetting(Integer settingId) {
        SystemSetting setting = systemSettingRepository.findById(settingId)
                .orElseThrow(() -> new RuntimeException("Setting not found"));
        systemSettingRepository.delete(setting);
    }
    
    @Transactional
    public void activateDeactivateSetting(Integer settingId, Boolean isActive) {
        SystemSetting setting = systemSettingRepository.findById(settingId)
                .orElseThrow(() -> new RuntimeException("Setting not found"));
        setting.setIsActive(isActive);
        systemSettingRepository.save(setting);
    }
    
    private SystemSettingResponse mapToSystemSettingResponse(SystemSetting setting) {
        SystemSettingResponse response = new SystemSettingResponse();
        response.setSettingId(setting.getSettingId());
        response.setSettingKey(setting.getSettingKey());
        response.setSettingValue(setting.getSettingValue());
        response.setSettingType(setting.getSettingType());
        response.setDescription(setting.getDescription());
        response.setIsActive(setting.getIsActive());
        response.setCreatedAt(setting.getCreatedAt());
        response.setUpdatedAt(setting.getUpdatedAt());
        return response;
    }
}
