package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemSettingRequest {
    
    @NotBlank(message = "Setting key is required")
    private String settingKey;
    
    private String settingValue;
    private String settingType; // STRING, NUMBER, BOOLEAN, JSON
    private String description;
    private Boolean isActive;
}
