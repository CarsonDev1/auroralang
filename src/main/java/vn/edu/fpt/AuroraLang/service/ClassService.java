package vn.edu.fpt.AuroraLang.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.request.ClassRequest;
import vn.edu.fpt.AuroraLang.dto.response.ClassResponse;
import vn.edu.fpt.AuroraLang.entity.Class;
import vn.edu.fpt.AuroraLang.entity.Course;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.repository.ClassRepository;
import vn.edu.fpt.AuroraLang.repository.CourseRepository;
import vn.edu.fpt.AuroraLang.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Page<ClassResponse> getManagerClasses(Integer managerId, Pageable pageable) {
        return classRepository.findByManager_UserId(managerId, pageable)
                .map(this::mapToResponse);
    }

    public Page<ClassResponse> searchManagerClasses(Integer managerId, String keyword, Pageable pageable) {
        return classRepository.searchManagerClasses(managerId, keyword, pageable)
                .map(this::mapToResponse);
    }

    public Page<ClassResponse> getTeacherClasses(Integer teacherId, Pageable pageable) {
        return classRepository.findByTeacher_UserId(teacherId, pageable)
                .map(this::mapToResponse);
    }

    public Page<ClassResponse> searchTeacherClasses(Integer teacherId, String keyword, Pageable pageable) {
        return classRepository.searchTeacherClasses(teacherId, keyword, pageable)
                .map(this::mapToResponse);
    }

    public ClassResponse getById(Integer classId) {
        Class c = classRepository.findById(classId).orElseThrow(() -> new RuntimeException("Class not found"));
        return mapToResponse(c);
    }

    @Transactional
    public ClassResponse create(Integer managerId, ClassRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        User manager = managerId != null ? userRepository.findById(managerId).orElse(null) : null;

        if (classRepository.findByClassCode(request.getClassCode()).isPresent()) {
            throw new RuntimeException("Class code already exists");
        }

        Class c = new Class();
        c.setClassCode(request.getClassCode());
        c.setClassName(request.getClassName());
        c.setDescription(request.getDescription());
        c.setCourse(course);
        c.setTeacher(teacher);
        c.setManager(manager);
        c.setStartDate(request.getStartDate());
        c.setEndDate(request.getEndDate());
        c.setMaxStudents(request.getMaxStudents());
        c.setScheduleInfo(request.getScheduleInfo());
        c.setRoomInfo(request.getRoomInfo());
        c.setStatus(request.getStatus() != null ? Class.Status.valueOf(request.getStatus()) : Class.Status.PLANNED);
        c.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : false);

        Class saved = classRepository.save(c);
        return mapToResponse(saved);
    }

    @Transactional
    public ClassResponse update(Integer classId, ClassRequest request) {
        Class c = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        // Validate required fields for update
        if (request.getClassName() == null || request.getClassName().trim().isEmpty()) {
            throw new RuntimeException("Class name is required");
        }

        c.setClassName(request.getClassName());
        if (request.getDescription() != null) c.setDescription(request.getDescription());
        if (request.getStartDate() != null) c.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) c.setEndDate(request.getEndDate());
        if (request.getMaxStudents() != null) c.setMaxStudents(request.getMaxStudents());
        if (request.getScheduleInfo() != null) c.setScheduleInfo(request.getScheduleInfo());
        if (request.getRoomInfo() != null) c.setRoomInfo(request.getRoomInfo());
        if (request.getStatus() != null) c.setStatus(Class.Status.valueOf(request.getStatus()));
        if (request.getIsPublic() != null) c.setIsPublic(request.getIsPublic());

        if (request.getTeacherId() != null) {
            User teacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            c.setTeacher(teacher);
        }

        if (request.getCourseId() != null) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            c.setCourse(course);
        }

        Class updated = classRepository.save(c);
        return mapToResponse(updated);
    }

    private ClassResponse mapToResponse(Class c) {
        ClassResponse r = new ClassResponse();
        r.setClassId(c.getClassId());
        r.setClassCode(c.getClassCode());
        r.setClassName(c.getClassName());
        r.setDescription(c.getDescription());
        r.setCourseId(c.getCourse() != null ? c.getCourse().getCourseId() : null);
        r.setCourseName(c.getCourse() != null ? c.getCourse().getCourseName() : null);
        r.setTeacherId(c.getTeacher() != null ? c.getTeacher().getUserId() : null);
        r.setTeacherName(c.getTeacher() != null ? c.getTeacher().getFullName() : null);
        r.setManagerId(c.getManager() != null ? c.getManager().getUserId() : null);
        r.setManagerName(c.getManager() != null ? c.getManager().getFullName() : null);
        r.setStartDate(c.getStartDate());
        r.setEndDate(c.getEndDate());
        r.setMaxStudents(c.getMaxStudents());
        r.setCurrentStudents(c.getCurrentStudents());
        r.setScheduleInfo(c.getScheduleInfo());
        r.setRoomInfo(c.getRoomInfo());
        r.setStatus(c.getStatus() != null ? c.getStatus().name() : null);
        r.setIsPublic(c.getIsPublic());
        return r;
    }
}


