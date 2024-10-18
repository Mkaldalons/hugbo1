package hugbo1.backend.Courses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByCourseId(String courseId);
    Course findByCourseName(String name);
    void deleteByCourseId(String courseId);
    void deleteByCourseName(String courseName);
}
