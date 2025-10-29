package vn.edu.fpt.AuroraLang.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequest {
    
    @NotNull(message = "Course ID or Class ID is required")
    private Integer entityId; // courseId or classId
    
    private String enrollmentType; // SELF, MANAGER_ASSIGNED, ADMIN_ASSIGNED, TEACHER_ADDED
}

