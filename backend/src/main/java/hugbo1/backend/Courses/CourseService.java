package hugbo1.backend.Courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public void addCourse(Course course) {
        courseRepository.save(course);
    }
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course getCourseById(String course_id) {
        return courseRepository.findByCourseId(course_id);
    }
    public void deleteCourseById(String course_id) {
        courseRepository.deleteById(course_id);
    }
    public void deleteCourseByName(String course_name) {
        courseRepository.deleteByCourseName(course_name);
    }
    public boolean doesCourseExist(String course_id) {
        return courseRepository.existsById(course_id);
    }

}
