package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "class_enrollments", 
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_class", columnNames = {"user_id", "class_id"})
    },
    indexes = {
        @Index(name = "idx_class_students", columnList = "class_id, status")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassEnrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_enrollment_id")
    private Integer classEnrollmentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private Class classEntity;
    
    @CreationTimestamp
    @Column(name = "enrollment_date", nullable = false, updatable = false)
    private LocalDateTime enrollmentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "enrollment_type")
    private EnrollmentType enrollmentType = EnrollmentType.SELF;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;
    
    @Column(name = "attendance_rate", precision = 5, scale = 2)
    private BigDecimal attendanceRate = BigDecimal.ZERO;
    
    @Column(name = "final_grade", precision = 5, scale = 2)
    private BigDecimal finalGrade;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrolled_by")
    private User enrolledBy;
    
    public enum EnrollmentType {
        SELF, TEACHER_ADDED, MANAGER_ASSIGNED, IMPORT
    }
    
    public enum Status {
        ACTIVE, COMPLETED, DROPPED, SUSPENDED
    }
}

