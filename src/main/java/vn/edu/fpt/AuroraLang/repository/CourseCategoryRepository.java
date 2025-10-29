package vn.edu.fpt.AuroraLang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.AuroraLang.entity.CourseCategory;

import java.util.List;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Integer> {
    List<CourseCategory> findByIsActive(Boolean isActive);
    List<CourseCategory> findByParentCategory_CategoryId(Integer parentCategoryId);
    List<CourseCategory> findByParentCategoryIsNull();
    List<CourseCategory> findByIsActiveOrderByDisplayOrderAsc(Boolean isActive);
}

