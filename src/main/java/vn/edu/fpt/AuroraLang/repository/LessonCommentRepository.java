package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.LessonComment;

import java.util.List;

@Repository
public interface LessonCommentRepository extends JpaRepository<LessonComment, Integer> {
    Page<LessonComment> findByLesson_LessonIdAndParentCommentIsNull(Integer lessonId, Pageable pageable);
    List<LessonComment> findByParentComment_CommentId(Integer parentCommentId);
    Page<LessonComment> findByLesson_LessonIdAndIsApproved(Integer lessonId, Boolean isApproved, Pageable pageable);
}

