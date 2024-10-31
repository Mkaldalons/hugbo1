package hugbo1.backend.Courses;

import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentRepository;
import hugbo1.backend.Users.Instructor;
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
        course.getStudents().add(student);
        student.getCourses().add(course);

        courseRepository.save(course);
    }
    //Breyta þessu í instructor seinna
    public List<Course> getAllCoursesByInstructor(String userName) {
        return courseRepository.findByInstructor(userName);
    }

}
