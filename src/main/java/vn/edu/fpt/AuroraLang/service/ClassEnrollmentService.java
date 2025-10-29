package vn.edu.fpt.AuroraLang.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.response.StudentResponse;
import vn.edu.fpt.AuroraLang.entity.Class;
import vn.edu.fpt.AuroraLang.entity.ClassEnrollment;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.repository.ClassEnrollmentRepository;
import vn.edu.fpt.AuroraLang.repository.ClassRepository;
import vn.edu.fpt.AuroraLang.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ClassEnrollmentService {

    private final ClassEnrollmentRepository enrollmentRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    public Page<StudentResponse> getManagerStudents(Integer managerId, Pageable pageable) {
        return enrollmentRepository.findByManagerId(managerId, pageable)
                .map(this::mapToStudentResponse);
    }

    @Transactional
    public void enrollStudentToClass(Integer classId, Integer studentId, Integer managerId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        
        if (classEntity.getManager() == null || !classEntity.getManager().getUserId().equals(managerId)) {
            throw new RuntimeException("You don't manage this class");
        }

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (enrollmentRepository.existsByUser_UserIdAndClassEntity_ClassId(studentId, classId)) {
            throw new RuntimeException("Student already enrolled");
        }

        ClassEnrollment enrollment = new ClassEnrollment();
        enrollment.setUser(student);
        enrollment.setClassEntity(classEntity);
        enrollment.setEnrollmentType(ClassEnrollment.EnrollmentType.MANAGER_ASSIGNED);
        enrollment.setStatus(ClassEnrollment.Status.ACTIVE);
        
        User manager = userRepository.findById(managerId).orElse(null);
        enrollment.setEnrolledBy(manager);
        
        enrollmentRepository.save(enrollment);
        
        // Update current students count
        classEntity.setCurrentStudents(classEntity.getCurrentStudents() + 1);
        classRepository.save(classEntity);
    }

    private StudentResponse mapToStudentResponse(ClassEnrollment e) {
        User u = e.getUser();
        StudentResponse r = new StudentResponse();
        r.setUserId(u.getUserId());
        r.setUsername(u.getUsername());
        r.setEmail(u.getEmail());
        r.setFullName(u.getFullName());
        r.setPhone(u.getPhone());
        r.setClassId(e.getClassEntity().getClassId());
        r.setClassName(e.getClassEntity().getClassName());
        r.setEnrollmentDate(e.getEnrollmentDate());
        r.setStatus(e.getStatus() != null ? e.getStatus().name() : null);
        return r;
    }
}

