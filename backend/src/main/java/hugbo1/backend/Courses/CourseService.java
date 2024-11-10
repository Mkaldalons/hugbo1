package hugbo1.backend.Courses;

import hugbo1.backend.Assignments.Assignment;
import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentRepository;
import hugbo1.backend.Assignments.AssignmentRepository;
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
    @Autowired
    private AssignmentRepository assignmentRepository;

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
    public List<Assignment> getAllAssignments(String courseId){
        return assignmentRepository.findAssignmentsByCourseId(courseId);
    }

    public boolean removeStudentFromCourse(String courseId, String studentId) {
        Optional<Course> courseOpt = getCourseById(courseId);
    
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            
            Optional<Student> studentOpt = course.getStudents().stream()
                .filter(student -> student.getStudentId() == Integer.parseInt(studentId))
                .findFirst();
    
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                
                course.getStudents().remove(student);
                student.getCourses().remove(course);
                
                courseRepository.save(course);
                studentRepository.save(student);
                
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
