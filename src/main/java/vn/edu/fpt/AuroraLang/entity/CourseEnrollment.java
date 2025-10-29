package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_enrollments", 
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_course", columnNames = {"user_id", "course_id"})
    },
    indexes = {
        @Index(name = "idx_user_enrollments", columnList = "user_id, status"),
        @Index(name = "idx_course_enrollments", columnList = "course_id, status"),
        @Index(name = "idx_enrollment_user_status", columnList = "user_id, status, enrollment_date")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Integer enrollmentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @CreationTimestamp
    @Column(name = "enrollment_date", nullable = false, updatable = false)
    private LocalDateTime enrollmentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "enrollment_type")
    private EnrollmentType enrollmentType = EnrollmentType.SELF;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;
    
    @Column(name = "progress_percentage", precision = 5, scale = 2)
    private BigDecimal progressPercentage = BigDecimal.ZERO;
    
    @Column(name = "completed_lessons")
    private Integer completedLessons = 0;
    
    @Column(name = "completed_tests")
    private Integer completedTests = 0;
    
    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;
    
    @Column(name = "completion_date")
    private LocalDateTime completionDate;
    
    @Column(name = "certificate_url", length = 500)
    private String certificateUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrolled_by")
    private User enrolledBy;
    
    public enum EnrollmentType {
        SELF, MANAGER_ASSIGNED, ADMIN_ASSIGNED
    }
    
    public enum Status {
        ACTIVE, COMPLETED, DROPPED, EXPIRED
    }
}

