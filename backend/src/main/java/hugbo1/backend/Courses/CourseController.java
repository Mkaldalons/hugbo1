package hugbo1.backend.Courses;

import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentService;
import hugbo1.backend.Assignments.*;
import hugbo1.backend.Users.User;
import hugbo1.backend.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private final CourseService courseService;
    @Autowired
    private final StudentService studentService;
    @Autowired
    private final UserService userService;

    public CourseController(CourseService courseService, StudentService studentService, UserService userService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createCourse(@RequestBody CourseRequest courseRequest) {
        Course course = new Course();
        course.setCourseName(courseRequest.getCourseName());
        course.setDescription(courseRequest.getDescription());
        course.setCourseId(course.getCourseId());
        course.setInstructor(courseRequest.getInstructor());

        courseService.addCourse(course);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Course created");
        return ResponseEntity.ok(responseBody);
    }
    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{userName}")
    public List<Course> getMyCourses(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        List<Course> courses = new ArrayList<>();
        if (user.getisInstructor())
        {
            courses.addAll(courseService.getAllCoursesByInstructor(userName));
        }
        else
        {
            Student student = studentService.getStudentByUserName(userName);
            courses.addAll(courseService.getAllCoursesByStudentId(student.getStudentId()));
        }
        return courses;
    }

    @PostMapping("/{courseId}/{userName}")
    public ResponseEntity<Map<String, Object>> registerStudentInCourse(@PathVariable Integer courseId, @PathVariable String userName) {
        Optional<Course> course = courseService.getCourseById(courseId);
        Map<String, Object> responseBody = new HashMap<>();

        Student student = studentService.getStudentByUserName(userName);
        if (student != null) {
            if (courseService.getAllStudents(courseId).contains(student)) {
                responseBody.put("message", "Student is already in this course");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            } else {
                if (course.isPresent()) {
                    courseService.registerStudentToCourse(student, course.get());
                    responseBody.put("message", "Student registered successfully");
                    return ResponseEntity.ok(responseBody);
                } else {
                    responseBody.put("message", "Course not found");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
                }
            }
        }
        responseBody.put("message", "Student not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>> getAllStudentsByCourseId(@PathVariable Integer courseId) {
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Student> students = courseService.getAllStudents(courseId);
        
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{courseId}/students/grades")
    public ResponseEntity<List<Map<String, Object>>> getStudentByGradeCriteria(
    @PathVariable Integer courseId, @RequestParam Double grade) {

    Optional<Course> course = courseService.getCourseById(courseId);
    if (course.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    List<Student> students = courseService.getAllStudents(courseId);

    List<Map<String, Object>> studentsWithGrades = courseService.calculateStudentGrades(courseId, students);

    List<Map<String, Object>> filteredStudentGrades = studentsWithGrades.stream()
        .filter(student -> {
            Double averageGrade = (Double) student.get("averageGrade");
            return averageGrade != null && averageGrade <= grade;
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(filteredStudentGrades);
}



    @GetMapping("/{courseId}/assignments")
    public ResponseEntity<List<Assignment>> getAllAssignmentsByCourseId(@PathVariable Integer courseId){
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Assignment> assignments = courseService.getAllAssignments(courseId);
        return ResponseEntity.ok(assignments);
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Map<String, Object>> deleteStudentFromCourse(
            @PathVariable Integer courseId,
            @PathVariable String studentId) {

        Map<String, Object> response = new HashMap<>();
        Optional<Course> course = courseService.getCourseById(courseId);

        if (course.isEmpty()) {
            response.put("message", "Course not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        boolean isRemoved = courseService.removeStudentFromCourse(courseId, studentId);
        if (isRemoved) {
            response.put("message", "Student removed from course");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Student not found in this course");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer courseId) {
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<Map<String, String>> updateCourseDetails(@PathVariable Integer courseId, @RequestBody CourseRequest updates) {
        Map<String, String> response = new HashMap<>();
        if (!updates.getCourseName().isEmpty()) {
            courseService.updateCourseName(courseId, updates.getCourseName());
            response.put("message", "Course name updated successfully");
            return ResponseEntity.ok(response);
        }
        if(!updates.getDescription().isEmpty()) {
            courseService.updateCourseDescription(courseId, updates.getDescription());
            response.put("message", "Course description updated successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
