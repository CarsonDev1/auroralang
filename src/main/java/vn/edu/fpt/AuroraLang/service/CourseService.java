package vn.edu.fpt.AuroraLang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.AuroraLang.dto.request.CourseRequest;
import vn.edu.fpt.AuroraLang.dto.response.CourseResponse;
import vn.edu.fpt.AuroraLang.entity.Course;
import vn.edu.fpt.AuroraLang.entity.CourseCategory;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.repository.CourseCategoryRepository;
import vn.edu.fpt.AuroraLang.repository.CourseRepository;
import vn.edu.fpt.AuroraLang.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    
    private final CourseRepository courseRepository;
    private final CourseCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    
    public Page<CourseResponse> getPublicCourses(Pageable pageable) {
        return courseRepository.findByIsPublicAndIsActiveAndStatus(
                true, true, Course.Status.PUBLISHED, pageable
        ).map(this::mapToCourseResponse);
    }
    
    public CourseResponse getCourseById(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return mapToCourseResponse(course);
    }
    
    public Page<CourseResponse> getExpertCourses(Integer expertId, Pageable pageable) {
        return courseRepository.findByCreatedBy_UserId(expertId, pageable)
                .map(this::mapToCourseResponse);
    }
    
    @Transactional
    public CourseResponse createCourse(Integer expertId, CourseRequest request) {
        User expert = userRepository.findById(expertId)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        
        if (courseRepository.findByCourseCode(request.getCourseCode()).isPresent()) {
            throw new RuntimeException("Course code already exists");
        }
        
        Course course = new Course();
        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setLevel(Course.Level.valueOf(request.getLevel()));
        course.setLanguage(request.getLanguage());
        course.setDurationWeeks(request.getDurationWeeks());
        course.setPrice(request.getPrice());
        course.setDiscountPrice(request.getDiscountPrice());
        course.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : false);
        course.setStatus(request.getStatus() != null ? Course.Status.valueOf(request.getStatus()) : Course.Status.DRAFT);
        course.setCreatedBy(expert);
        
        if (request.getCategoryId() != null) {
            CourseCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            course.setCategory(category);
        }
        
        Course savedCourse = courseRepository.save(course);
        return mapToCourseResponse(savedCourse);
    }
    
    @Transactional
    public CourseResponse updateCourse(Integer courseId, CourseRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (request.getCourseName() != null) course.setCourseName(request.getCourseName());
        if (request.getDescription() != null) course.setDescription(request.getDescription());
        if (request.getThumbnailUrl() != null) course.setThumbnailUrl(request.getThumbnailUrl());
        if (request.getLevel() != null) course.setLevel(Course.Level.valueOf(request.getLevel()));
        if (request.getLanguage() != null) course.setLanguage(request.getLanguage());
        if (request.getDurationWeeks() != null) course.setDurationWeeks(request.getDurationWeeks());
        if (request.getPrice() != null) course.setPrice(request.getPrice());
        if (request.getDiscountPrice() != null) course.setDiscountPrice(request.getDiscountPrice());
        if (request.getIsPublic() != null) course.setIsPublic(request.getIsPublic());
        if (request.getStatus() != null) {
            Course.Status newStatus = Course.Status.valueOf(request.getStatus());
            course.setStatus(newStatus);
            if (newStatus == Course.Status.PUBLISHED && course.getPublishedAt() == null) {
                course.setPublishedAt(LocalDateTime.now());
            }
        }
        
        if (request.getCategoryId() != null) {
            CourseCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            course.setCategory(category);
        }
        
        Course updatedCourse = courseRepository.save(course);
        return mapToCourseResponse(updatedCourse);
    }
    
    @Transactional
    public void deleteCourse(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.delete(course);
    }
    
    @Transactional
    public void activateDeactivateCourse(Integer courseId, Boolean isActive) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setIsActive(isActive);
        courseRepository.save(course);
    }
    
    public Page<CourseResponse> searchPublicCourses(String keyword, Pageable pageable) {
        return courseRepository.searchPublicCourses(keyword, pageable)
                .map(this::mapToCourseResponse);
    }
    
    public List<CourseResponse> getPopularCourses() {
        return courseRepository.findTop10ByIsPublicAndIsActiveAndStatusOrderByTotalEnrollmentsDesc(
                true, true, Course.Status.PUBLISHED
        ).stream().map(this::mapToCourseResponse).collect(Collectors.toList());
    }
    
    private CourseResponse mapToCourseResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setCourseId(course.getCourseId());
        response.setCourseCode(course.getCourseCode());
        response.setCourseName(course.getCourseName());
        response.setDescription(course.getDescription());
        response.setThumbnailUrl(course.getThumbnailUrl());
        response.setCategoryName(course.getCategory() != null ? course.getCategory().getCategoryName() : null);
        response.setLevel(course.getLevel().name());
        response.setLanguage(course.getLanguage());
        response.setDurationWeeks(course.getDurationWeeks());
        response.setPrice(course.getPrice());
        response.setDiscountPrice(course.getDiscountPrice());
        response.setIsPublic(course.getIsPublic());
        response.setStatus(course.getStatus().name());
        response.setTotalEnrollments(course.getTotalEnrollments());
        response.setTotalLessons(course.getTotalLessons());
        response.setTotalFlashcards(course.getTotalFlashcards());
        response.setTotalTests(course.getTotalTests());
        response.setAverageRating(course.getAverageRating());
        response.setTotalReviews(course.getTotalReviews());
        response.setCreatedByName(course.getCreatedBy().getFullName());
        response.setCreatedAt(course.getCreatedAt());
        response.setPublishedAt(course.getPublishedAt());
        return response;
    }
}

