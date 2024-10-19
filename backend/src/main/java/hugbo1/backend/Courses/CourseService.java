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
    public boolean doesCourseExist(String id) {
        return courseRepository.existsById(id);
    }
    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

}
