package vn.edu.fpt.AuroraLang.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClassResponse {
    private Integer classId;
    private String classCode;
    private String className;
    private String description;
    private Integer courseId;
    private String courseName;
    private Integer teacherId;
    private String teacherName;
    private Integer managerId;
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
