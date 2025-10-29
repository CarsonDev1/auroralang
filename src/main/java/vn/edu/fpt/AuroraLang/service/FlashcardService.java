package vn.edu.fpt.AuroraLang.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.fpt.AuroraLang.dto.request.FlashcardRequest;
import vn.edu.fpt.AuroraLang.dto.response.FlashcardResponse;
import vn.edu.fpt.AuroraLang.entity.Course;
import vn.edu.fpt.AuroraLang.entity.Flashcard;
import vn.edu.fpt.AuroraLang.entity.User;
import vn.edu.fpt.AuroraLang.repository.CourseRepository;
import vn.edu.fpt.AuroraLang.repository.FlashcardRepository;
import vn.edu.fpt.AuroraLang.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Page<FlashcardResponse> getUserFlashcards(Integer userId, Pageable pageable) {
        return flashcardRepository.findByUser_UserId(userId, pageable)
                .map(this::mapToResponse);
    }

    public Page<FlashcardResponse> getCourseFlashcards(Integer courseId, Pageable pageable) {
        return flashcardRepository.findByCourse_CourseId(courseId, pageable)
                .map(this::mapToResponse);
    }

    public FlashcardResponse getById(Integer flashcardId) {
        Flashcard flashcard = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));
        return mapToResponse(flashcard);
    }

    @Transactional
    public FlashcardResponse create(Integer userId, FlashcardRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Flashcard flashcard = new Flashcard();
        flashcard.setUser(user);
        flashcard.setFrontText(request.getFrontText());
        flashcard.setBackText(request.getBackText());
        flashcard.setFrontAudioUrl(request.getFrontAudioUrl());
        flashcard.setBackAudioUrl(request.getBackAudioUrl());
        flashcard.setFrontImageUrl(request.getFrontImageUrl());
        flashcard.setBackImageUrl(request.getBackImageUrl());
        flashcard.setPronunciation(request.getPronunciation());
        flashcard.setExampleSentence(request.getExampleSentence());
        flashcard.setNote(request.getNote());
        flashcard.setTags(request.getTags());
        flashcard.setIsPublic(request.getIsPublic() != null ? request.getIsPublic().booleanValue() : false);
        
        if (request.getDifficulty() != null) {
            flashcard.setDifficulty(Flashcard.Difficulty.valueOf(request.getDifficulty()));
        }
        
        if (request.getCourseId() != null) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            flashcard.setCourse(course);
        }

        Flashcard saved = flashcardRepository.save(flashcard);
        return mapToResponse(saved);
    }

    @Transactional
    public FlashcardResponse update(Integer flashcardId, FlashcardRequest request) {
        Flashcard flashcard = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));

        if (request.getFrontText() != null) flashcard.setFrontText(request.getFrontText());
        if (request.getBackText() != null) flashcard.setBackText(request.getBackText());
        if (request.getFrontAudioUrl() != null) flashcard.setFrontAudioUrl(request.getFrontAudioUrl());
        if (request.getBackAudioUrl() != null) flashcard.setBackAudioUrl(request.getBackAudioUrl());
        if (request.getFrontImageUrl() != null) flashcard.setFrontImageUrl(request.getFrontImageUrl());
        if (request.getBackImageUrl() != null) flashcard.setBackImageUrl(request.getBackImageUrl());
        if (request.getPronunciation() != null) flashcard.setPronunciation(request.getPronunciation());
        if (request.getExampleSentence() != null) flashcard.setExampleSentence(request.getExampleSentence());
        if (request.getNote() != null) flashcard.setNote(request.getNote());
        if (request.getTags() != null) flashcard.setTags(request.getTags());
        if (request.getIsPublic() != null) flashcard.setIsPublic(request.getIsPublic());
        
        if (request.getDifficulty() != null) {
            flashcard.setDifficulty(Flashcard.Difficulty.valueOf(request.getDifficulty()));
        }

        Flashcard updated = flashcardRepository.save(flashcard);
        return mapToResponse(updated);
    }

    @Transactional
    public void delete(Integer flashcardId) {
        flashcardRepository.deleteById(flashcardId);
    }

    private FlashcardResponse mapToResponse(Flashcard f) {
        FlashcardResponse r = new FlashcardResponse();
        r.setFlashcardId(f.getFlashcardId());
        r.setFrontText(f.getFrontText());
        r.setBackText(f.getBackText());
        r.setFrontAudioUrl(f.getFrontAudioUrl());
        r.setBackAudioUrl(f.getBackAudioUrl());
        r.setFrontImageUrl(f.getFrontImageUrl());
        r.setBackImageUrl(f.getBackImageUrl());
        r.setPronunciation(f.getPronunciation());
        r.setExampleSentence(f.getExampleSentence());
        r.setNote(f.getNote());
        r.setDifficulty(f.getDifficulty() != null ? f.getDifficulty().name() : null);
        r.setTags(f.getTags());
        r.setIsPublic(f.getIsPublic());
        r.setCourseId(f.getCourse() != null ? f.getCourse().getCourseId() : null);
        r.setCourseName(f.getCourse() != null ? f.getCourse().getCourseName() : null);
        r.setUserId(f.getUser() != null ? f.getUser().getUserId() : null);
        r.setUserName(f.getUser() != null ? f.getUser().getFullName() : null);
        r.setCreatedAt(f.getCreatedAt());
        return r;
    }
}
