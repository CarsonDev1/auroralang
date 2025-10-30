package vn.edu.fpt.AuroraLang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // <--- CHẶN VIEW ADMIN
                        .requestMatchers("/", "/home", "/about", "/contact").permitAll()
                        // ✅ Guest course pages
                        .requestMatchers("/courses/**").permitAll()

                        // ✅ Permit API Public
                        .requestMatchers("/ping", "/api/ping").permitAll()
                        .requestMatchers("/api/auth/**", "/api/public/**").permitAll()
                        // ✅ Public course APIs
                        .requestMatchers("/api/courses/public/**").permitAll()
                        // ✅ VNPay create + return (public)
                        .requestMatchers("/api/payments/create").permitAll()
                        .requestMatchers("/api/payments/vnpay-return").permitAll()
                        .requestMatchers("/api/debug/**").permitAll()
                        .requestMatchers("/api/test/**").permitAll()
                        .requestMatchers("/api/select/**").hasAnyRole("MANAGER", "ADMIN", "EXPERT", "TEACHER")

                        // ✅ Role-based API Access
                        .requestMatchers("/api/student/**")
                        .hasAnyRole("STUDENT", "TEACHER", "EXPERT", "MANAGER", "ADMIN")
                        .requestMatchers("/api/teacher/**").hasAnyRole("TEACHER", "MANAGER", "ADMIN")
                        .requestMatchers("/api/expert/**").hasAnyRole("EXPERT", "ADMIN")
                        .requestMatchers("/api/manager/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // ✅ Tất cả còn lại yêu cầu login
                        .anyRequest().authenticated());

        // ✅ JWT cho API
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
