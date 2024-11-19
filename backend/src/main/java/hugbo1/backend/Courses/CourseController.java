package hugbo1.backend.Courses;

import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentService;
import hugbo1.backend.Assignments.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/my-courses/{userName}")
    public List<Course> getMyCourses(@PathVariable String userName) {
        return courseService.getAllCoursesByInstructor(userName);
    }

    @GetMapping("/my-courses-student/{userName}")
    public List<Course> getMyCoursesStudent(@PathVariable String userName) {
        Student student = studentService.getStudentByUserName(userName);
        return courseService.getAllCoursesByStudentId(student.getStudentId());
    }

    @PostMapping("/students/add")
    public ResponseEntity<Map<String, Object>> registerStudentInCourse(@RequestBody RegisterRequest registerRequest){
        Student student = studentService.getStudentByUserName(registerRequest.getUserName());
        Optional<Course> course = courseService.getCourseById(registerRequest.getCourseId());
        Map<String, Object> responseBody = new HashMap<>();
        
        if (courseService.getAllStudents(registerRequest.getCourseId()).contains(student)) {
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

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<Student>> getAllStudentsByCourseId(@PathVariable String courseId) {
        System.out.println("Received courseId in /courses/{courseId}/students: " + courseId); // Print courseId
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Student> students = courseService.getAllStudents(courseId);
        
        return ResponseEntity.ok(students);
    }

    @GetMapping("/courses/{courseId}/students/grades")
    public ResponseEntity<List<Map<String, Object>>> getStudentByGradeCriteria(
    @PathVariable String courseId, @RequestParam Double grade) {

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



    @GetMapping("/courses/{courseId}/assignments")
    public ResponseEntity<List<Assignment>> getAllAssignmentsByCourseId(@PathVariable String courseId){
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Assignment> assignments = courseService.getAllAssignments(courseId);
        return ResponseEntity.ok(assignments);
    }

    @DeleteMapping("/courses/{courseId}/students/{studentId}")
    public ResponseEntity<Map<String, Object>> deleteStudentFromCourse(
            @PathVariable String courseId,
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
    public ResponseEntity<Course> getCourseById(@PathVariable String courseId) {
        System.out.println("Received courseId in /courses/{courseId}: " + courseId); // Print courseId received
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<Map<String, String>> updateCourseName(@PathVariable String courseId, @RequestBody Map<String, String> updates) {
        String newCourseName = updates.get("courseName");
        if (newCourseName == null || newCourseName.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid course name");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            courseService.updateCourseName(courseId, newCourseName);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Course name updated successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
