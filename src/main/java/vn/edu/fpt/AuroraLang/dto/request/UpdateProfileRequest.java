package vn.edu.fpt.AuroraLang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    private String fullName;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender; // MALE, FEMALE, OTHER
    private String address;
    private String avatarUrl;
}

