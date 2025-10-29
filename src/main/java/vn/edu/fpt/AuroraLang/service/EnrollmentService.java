package vn.edu.fpt.AuroraLang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.AuroraLang.entity.Course;
import vn.edu.fpt.AuroraLang.entity.CourseEnrollment;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.repository.CourseEnrollmentRepository;
import vn.edu.fpt.AuroraLang.repository.CourseRepository;
import vn.edu.fpt.AuroraLang.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    
    private final CourseEnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public void enrollCourse(Integer userId, Integer courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        // Check if already enrolled
        if (enrollmentRepository.existsByUser_UserIdAndCourse_CourseId(userId, courseId)) {
            throw new RuntimeException("Already enrolled in this course");
        }
        
        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrollmentType(CourseEnrollment.EnrollmentType.SELF);
        enrollment.setStatus(CourseEnrollment.Status.ACTIVE);
        enrollmentRepository.save(enrollment);
        
        // Update course total enrollments
        course.setTotalEnrollments(course.getTotalEnrollments() + 1);
        courseRepository.save(course);
    }
    
    public Page<CourseEnrollment> getUserEnrollments(Integer userId, Pageable pageable) {
        return enrollmentRepository.findByUser_UserId(userId, pageable);
    }
    
    public Page<CourseEnrollment> getCourseEnrollments(Integer courseId, Pageable pageable) {
        return enrollmentRepository.findByCourse_CourseId(courseId, pageable);
    }
    
    public boolean isUserEnrolled(Integer userId, Integer courseId) {
        return enrollmentRepository.existsByUser_UserIdAndCourse_CourseId(userId, courseId);
    }
}

