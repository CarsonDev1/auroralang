package vn.edu.fpt.AuroraLang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private UserResponse user;
    private List<String> roles;
    
    public AuthResponse(String token, UserResponse user, List<String> roles) {
        this.token = token;
        this.user = user;
        this.roles = roles;
    }
}

