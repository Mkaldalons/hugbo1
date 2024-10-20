package hugbo1.backend.Courses;

import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentService;
import hugbo1.backend.Users.User;
import hugbo1.backend.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    @Autowired
    private final CourseService courseService;
    @Autowired
    private final StudentService studentService;

    public CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @PostMapping("/courses")
    public ResponseEntity<Map<String, Object>> createCourse(@RequestBody CourseRequest courseRequest) {
        Course course = new Course();
        course.setCourseName(courseRequest.getCourseName());
        course.setCourseId(course.getCourseId());
        course.setInstructor(courseRequest.getCreatedBy());
        if(courseService.doesCourseExist(course.getCourseId())) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Course already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }else {
            course.setDescription("missing");
            courseService.addCourse(course);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Course created");
            return ResponseEntity.ok(responseBody);
        }
    }
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/students/add")
    public ResponseEntity<Map<String, Object>> registerStudentInCourse(@RequestBody RegisterRequest registerRequest){
        System.out.println("COURSE ID IS: "+registerRequest.getCourseId() + " USERNAME IS: " + registerRequest.getUserName());
        Student student = studentService.getStudentByUserName(registerRequest.getUserName());
        Optional<Course> course = courseService.getCourseById(registerRequest.getCourseId());
        Map<String, Object> responseBody = new HashMap<>();
        if (courseService.getAllStudents(registerRequest.getCourseId()).contains(student)) {
            responseBody.put("message", "Student is already in this course");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }else {
            if (course.isPresent()) {
                courseService.registerStudentToCourse(student, course.get());
                responseBody.put("message", "Student registered successfully");
                System.out.println("Student registered successfully" + registerRequest.getUserName());
                return ResponseEntity.ok(responseBody);
            }else {
                responseBody.put("message", "Student not registered successfully");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }
        }
    }
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudentsByCourseId(@RequestParam String courseId) {
        System.out.println("COURSE ID IS: "+courseId);
        Optional<Course> course = courseService.getCourseById(courseId);
        if (!course.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Student> students = courseService.getAllStudents(courseId);
        System.out.println("Students: "+students);
        return ResponseEntity.ok(students);
    }
}
