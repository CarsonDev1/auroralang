package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "social_auth_accounts", 
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_provider_user", columnNames = {"provider", "provider_user_id"})
    },
    indexes = {
        @Index(name = "idx_provider_email", columnList = "provider, provider_email"),
        @Index(name = "idx_user_provider", columnList = "user_id, provider")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialAuthAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_auth_id")
    private Integer socialAuthId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider;
    
    @Column(name = "provider_user_id", nullable = false)
    private String providerUserId;
    
    @Column(name = "provider_username")
    private String providerUsername;
    
    @Column(name = "provider_email")
    private String providerEmail;
    
    @Column(name = "provider_avatar_url", length = 500)
    private String providerAvatarUrl;
    
    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;
    
    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;
    
    @Column(name = "token_expires_at")
    private LocalDateTime tokenExpiresAt;
    
    @Column(name = "provider_data", columnDefinition = "JSON")
    private String providerData;
    
    @CreationTimestamp
    @Column(name = "linked_at", nullable = false, updatable = false)
    private LocalDateTime linkedAt;
    
    @Column(name = "last_used_at")
    private LocalDateTime lastUsedAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    public enum Provider {
        GOOGLE, FACEBOOK, GITHUB, APPLE
    }
}

