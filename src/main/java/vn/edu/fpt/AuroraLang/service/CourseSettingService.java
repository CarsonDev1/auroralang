package vn.edu.fpt.AuroraLang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.AuroraLang.dto.request.CourseSettingRequest;
import vn.edu.fpt.AuroraLang.dto.response.CourseSettingResponse;
import vn.edu.fpt.AuroraLang.entity.Course;
import vn.edu.fpt.AuroraLang.entity.CourseSetting;
import vn.edu.fpt.AuroraLang.repository.CourseRepository;
import vn.edu.fpt.AuroraLang.repository.CourseSettingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseSettingService {
    
    private final CourseSettingRepository courseSettingRepository;
    private final CourseRepository courseRepository;
    
    public List<CourseSettingResponse> getCourseSettings(Integer courseId) {
        return courseSettingRepository.findByCourse_CourseId(courseId).stream()
                .map(this::mapToCourseSettingResponse)
                .collect(Collectors.toList());
    }
    
    public CourseSettingResponse getCourseSettingById(Integer courseId, Integer settingId) {
        CourseSetting setting = courseSettingRepository.findById(settingId)
                .orElseThrow(() -> new RuntimeException("Course setting not found"));
        
        if (!setting.getCourse().getCourseId().equals(courseId)) {
            throw new RuntimeException("Setting does not belong to this course");
        }
        
        return mapToCourseSettingResponse(setting);
    }
    
    @Transactional
    public CourseSettingResponse createCourseSetting(Integer courseId, CourseSettingRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (courseSettingRepository.findByCourse_CourseIdAndSettingKey(courseId, request.getSettingKey()).isPresent()) {
            throw new RuntimeException("Setting key already exists for this course");
        }
        
        CourseSetting setting = new CourseSetting();
        setting.setCourse(course);
        setting.setSettingKey(request.getSettingKey());
        setting.setSettingValue(request.getSettingValue());
        setting.setDescription(request.getDescription());
        
        CourseSetting savedSetting = courseSettingRepository.save(setting);
        return mapToCourseSettingResponse(savedSetting);
    }
    
    @Transactional
    public CourseSettingResponse updateCourseSetting(Integer courseId, Integer settingId, CourseSettingRequest request) {
        CourseSetting setting = courseSettingRepository.findById(settingId)
                .orElseThrow(() -> new RuntimeException("Course setting not found"));
        
        if (!setting.getCourse().getCourseId().equals(courseId)) {
            throw new RuntimeException("Setting does not belong to this course");
        }
        
        if (request.getSettingValue() != null) setting.setSettingValue(request.getSettingValue());
        if (request.getDescription() != null) setting.setDescription(request.getDescription());
        
        CourseSetting updatedSetting = courseSettingRepository.save(setting);
        return mapToCourseSettingResponse(updatedSetting);
    }
    
    @Transactional
    public void deleteCourseSetting(Integer courseId, Integer settingId) {
        CourseSetting setting = courseSettingRepository.findById(settingId)
                .orElseThrow(() -> new RuntimeException("Course setting not found"));
        
        if (!setting.getCourse().getCourseId().equals(courseId)) {
            throw new RuntimeException("Setting does not belong to this course");
        }
        
        courseSettingRepository.delete(setting);
    }
    
    private CourseSettingResponse mapToCourseSettingResponse(CourseSetting setting) {
        CourseSettingResponse response = new CourseSettingResponse();
        response.setSettingId(setting.getSettingId());
        response.setCourseId(setting.getCourse().getCourseId());
        response.setCourseName(setting.getCourse().getCourseName());
        response.setSettingKey(setting.getSettingKey());
        response.setSettingValue(setting.getSettingValue());
        response.setDescription(setting.getDescription());
        response.setCreatedAt(setting.getCreatedAt());
        response.setUpdatedAt(setting.getUpdatedAt());
        return response;
    }
}
