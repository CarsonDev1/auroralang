package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassSessionRequest {
    
    private Integer lessonId;
    
    @NotNull(message = "Session number is required")
    private Integer sessionNumber;
    
    @NotBlank(message = "Session name is required")
    private String sessionName;
    
    @NotNull(message = "Session date is required")
    private LocalDate sessionDate;
    
    @NotNull(message = "Start time is required")
    private LocalTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalTime endTime;
    
    private String location;
    private String onlineMeetingUrl;
    private String description;
    private String status; // SCHEDULED, ONGOING, COMPLETED, CANCELLED
}

