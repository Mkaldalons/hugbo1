package hugbo1.backend.Courses;

import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentRepository;
import hugbo1.backend.Users.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
    public List<Course> getAllCoursesByInstructor(String userName) {
        return courseRepository.findByInstructor(userName);
    }

    public boolean removeStudentFromCourse(String courseId, String studentId) {
    Optional<Course> courseOpt = getCourseById(courseId);

    if (courseOpt.isPresent()) {
        Course course = courseOpt.get();
        List<Student> students = new ArrayList<>(course.getStudents());

        Optional<Student> studentOpt = students.stream()
        .filter(student -> student.getStudentId() == Integer.parseInt(studentId)) 
        .findFirst();

        if (studentOpt.isPresent()) {
            students.remove(studentOpt.get());
            course.setStudents(new HashSet<>(students));  
            courseRepository.save(course);  
            return true;  
        }
    }
    return false;  
} 
    public List<Student> findStudentsByCriteria(String courseId, String name, String username) {
        Optional<Course> courseOpt = getCourseById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            
            List<Student> students = new ArrayList<>(course.getStudents());
            
            return students.stream()
                    .filter(student -> (name == null || student.getName().equalsIgnoreCase(name)) &&
                                    (username == null || student.getUserName().equalsIgnoreCase(username)))
                    .toList();
        }
        return new ArrayList<>();
    }
}
