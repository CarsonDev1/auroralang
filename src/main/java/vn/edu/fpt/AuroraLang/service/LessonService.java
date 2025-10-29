package vn.edu.fpt.AuroraLang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.AuroraLang.dto.request.LessonRequest;
import vn.edu.fpt.AuroraLang.dto.response.LessonResponse;
import vn.edu.fpt.AuroraLang.entity.Course;
import vn.edu.fpt.AuroraLang.entity.Lesson;
import vn.edu.fpt.AuroraLang.repository.CourseRepository;
import vn.edu.fpt.AuroraLang.repository.LessonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    
    public List<LessonResponse> getCourseLessons(Integer courseId) {
        return lessonRepository.findByCourse_CourseIdOrderByLessonOrderAsc(courseId)
                .stream().map(this::mapToLessonResponse).collect(Collectors.toList());
    }
    
    public LessonResponse getLessonById(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        return mapToLessonResponse(lesson);
    }
    
    @Transactional
    public LessonResponse createLesson(Integer courseId, LessonRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setLessonCode(request.getLessonCode());
        lesson.setLessonName(request.getLessonName());
        lesson.setDescription(request.getDescription());
        lesson.setContent(request.getContent());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setAudioUrl(request.getAudioUrl());
        lesson.setDocumentUrl(request.getDocumentUrl());
        lesson.setLessonOrder(request.getLessonOrder());
        lesson.setDurationMinutes(request.getDurationMinutes());
        lesson.setLessonType(request.getLessonType() != null ? Lesson.LessonType.valueOf(request.getLessonType()) : Lesson.LessonType.READING);
        lesson.setIsPreview(request.getIsPreview() != null ? request.getIsPreview() : false);
        lesson.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        
        Lesson savedLesson = lessonRepository.save(lesson);
        
        // Update course total lessons
        course.setTotalLessons(course.getTotalLessons() + 1);
        courseRepository.save(course);
        
        return mapToLessonResponse(savedLesson);
    }
    
    @Transactional
    public LessonResponse updateLesson(Integer lessonId, LessonRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        
        if (request.getLessonName() != null) lesson.setLessonName(request.getLessonName());
        if (request.getDescription() != null) lesson.setDescription(request.getDescription());
        if (request.getContent() != null) lesson.setContent(request.getContent());
        if (request.getVideoUrl() != null) lesson.setVideoUrl(request.getVideoUrl());
        if (request.getAudioUrl() != null) lesson.setAudioUrl(request.getAudioUrl());
        if (request.getDocumentUrl() != null) lesson.setDocumentUrl(request.getDocumentUrl());
        if (request.getLessonOrder() != null) lesson.setLessonOrder(request.getLessonOrder());
        if (request.getDurationMinutes() != null) lesson.setDurationMinutes(request.getDurationMinutes());
        if (request.getLessonType() != null) lesson.setLessonType(Lesson.LessonType.valueOf(request.getLessonType()));
        if (request.getIsPreview() != null) lesson.setIsPreview(request.getIsPreview());
        if (request.getIsActive() != null) lesson.setIsActive(request.getIsActive());
        
        Lesson updatedLesson = lessonRepository.save(lesson);
        return mapToLessonResponse(updatedLesson);
    }
    
    @Transactional
    public void deleteLesson(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        
        Course course = lesson.getCourse();
        lessonRepository.delete(lesson);
        
        // Update course total lessons
        course.setTotalLessons(Math.max(0, course.getTotalLessons() - 1));
        courseRepository.save(course);
    }
    
    private LessonResponse mapToLessonResponse(Lesson lesson) {
        LessonResponse response = new LessonResponse();
        response.setLessonId(lesson.getLessonId());
        response.setLessonCode(lesson.getLessonCode());
        response.setLessonName(lesson.getLessonName());
        response.setDescription(lesson.getDescription());
        response.setContent(lesson.getContent());
        response.setVideoUrl(lesson.getVideoUrl());
        response.setAudioUrl(lesson.getAudioUrl());
        response.setDocumentUrl(lesson.getDocumentUrl());
        response.setLessonOrder(lesson.getLessonOrder());
        response.setDurationMinutes(lesson.getDurationMinutes());
        response.setLessonType(lesson.getLessonType().name());
        response.setIsPreview(lesson.getIsPreview());
        response.setIsActive(lesson.getIsActive());
        return response;
    }
}

