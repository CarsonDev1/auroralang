package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.CourseSetting;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseSettingRepository extends JpaRepository<CourseSetting, Integer> {
    List<CourseSetting> findByCourse_CourseId(Integer courseId);
    Optional<CourseSetting> findByCourse_CourseIdAndSettingKey(Integer courseId, String settingKey);
}

