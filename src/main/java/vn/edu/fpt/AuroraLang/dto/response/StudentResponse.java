package vn.edu.fpt.AuroraLang.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StudentResponse {
    private Integer userId;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private Integer classId;
    private String className;
    private LocalDateTime enrollmentDate;
    private String status;
}

