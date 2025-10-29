package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRequest {
    
    @NotBlank(message = "Class code is required")
    private String classCode;
    
    @NotBlank(message = "Class name is required")
    private String className;
    
    private String description;
    
    @NotNull(message = "Course ID is required")
    private Integer courseId;
    
    @NotNull(message = "Teacher ID is required")
    private Integer teacherId;
    
    private Integer managerId;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    private LocalDate endDate;
    private Integer maxStudents;
    private String scheduleInfo;
    private String roomInfo;
    private String status; // PLANNED, ONGOING, COMPLETED, CANCELLED
    private Boolean isPublic;
}

