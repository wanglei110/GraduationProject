package com.wang.graduationproject.Repository;

import com.wang.graduationproject.Dao.Course;
import com.wang.graduationproject.Dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    Course findCourseByIdAndIfDeletedFalse(Long id);

    Course findCourseByCourseCodeAndIfDeletedFalse(String courseCode);

    @Transactional
    @Modifying
    @Query(value = "delete from course_ideological_elements where ideological_elements_id=?1",nativeQuery = true)
    void deleteCourseByIdeoId(Long id);
}
