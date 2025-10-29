package vn.edu.fpt.AuroraLang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classes", indexes = {
    @Index(name = "idx_teacher_classes", columnList = "teacher_id"),
    @Index(name = "idx_class_status", columnList = "status"),
    @Index(name = "idx_class_teacher_status", columnList = "teacher_id, status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer classId;
    
    @Column(name = "class_code", nullable = false, unique = true, length = 50)
    private String classCode;
    
    @Column(name = "class_name", nullable = false)
    private String className;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "max_students")
    private Integer maxStudents = 30;
    
    @Column(name = "current_students")
    private Integer currentStudents = 0;
    
    @Column(name = "schedule_info", columnDefinition = "TEXT")
    private String scheduleInfo;
    
    @Column(name = "room_info")
    private String roomInfo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.PLANNED;
    
    @Column(name = "is_public")
    private Boolean isPublic = false;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClassSession> sessions = new HashSet<>();
    
    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClassEnrollment> enrollments = new HashSet<>();
    
    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClassLesson> classLessons = new HashSet<>();
    
    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClassFlashcard> classFlashcards = new HashSet<>();
    
    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClassTest> classTests = new HashSet<>();
    
    public enum Status {
        PLANNED, ONGOING, COMPLETED, CANCELLED
    }
}

