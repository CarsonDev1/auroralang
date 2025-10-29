package vn.edu.fpt.AuroraLang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassResponse {
    private Integer classId;
    private String classCode;
    private String className;
    private String description;
    private String courseName;
    private String teacherName;
    private String managerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxStudents;
    private Integer currentStudents;
    private String scheduleInfo;
    private String roomInfo;
    private String status;
    private Boolean isPublic;
}

