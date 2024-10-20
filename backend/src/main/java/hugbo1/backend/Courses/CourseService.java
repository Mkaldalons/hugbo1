package hugbo1.backend.Courses;

import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(String id) {
        return courseRepository.findById(id);
    }

    public boolean doesCourseExist(String id) {
        return courseRepository.existsById(id);
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    public List<Student> getAllStudents(String courseId) {
        return courseRepository.findStudentsByCourseId(courseId);
    }
    public void registerStudentToCourse(Student student, Course course) {
        // Ensure both sides of the relationship are updated
        course.getStudents().add(student);  // Add student to course
        student.getCourses().add(course);  // Add course to student (optional but recommended)

        // Save the course, which is the owning side
        courseRepository.save(course);  // Persist the relationship in the join table
    }

}
