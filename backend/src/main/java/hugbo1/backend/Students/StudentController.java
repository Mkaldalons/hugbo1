package hugbo1.backend.Students;

import hugbo1.backend.Assignments.Assignment;
import hugbo1.backend.Courses.Course;
import hugbo1.backend.Courses.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/student/{userName}/assignments/{assignmentId}")
    public boolean hasStudentSubmitted(@PathVariable String userName, @PathVariable int assignmentId) {
        Student student = studentService.getStudentByUserName(userName);
        return studentService.hasStudentSubmitted(student, assignmentId);
    }
    @GetMapping("/students-submitted-assignments/{userName}")
    public List<Assignment> getStudentsSubmittedAssignments(@PathVariable String userName) {
        return studentService.getAllSubmissionsByStudent(studentService.getStudentByUserName(userName));
    }
    @GetMapping("/grade/{assignmentId}/student/{userName}")
    public double getGradeForAssignment(@PathVariable int assignmentId, @PathVariable String userName){
        return studentService.getGradeForAssignment(studentService.getStudentByUserName(userName), assignmentId);
    }
    @GetMapping("/average-grade-student/{userName}")
    public double getAverageGradeForAssignments(@PathVariable String userName){
        return studentService.getAverageGradeForStudent(studentService.getStudentByUserName(userName));
    }
    @GetMapping("/average-grade-student-course/{courseId}")
    public double getAverageGradeForStudentCourse(@PathVariable String courseId, @RequestParam String userName){
        Optional<Course> course = courseService.getCourseById(courseId);
        Student student = studentService.getStudentByUserName(userName);
        return course.map(value -> studentService.getAverageFromCourse(value, student)).orElse(0.0);
    }
    @GetMapping("filtered-assignments/{userName}")
    public List<Assignment> getFilteredAssignments(@PathVariable String userName) {
        System.out.println("Username: "+userName);
        Student student = studentService.getStudentByUserName(userName);
        List<Course> courses = studentService.getAllCoursesForStudent(student);
        List<Assignment> assignments = new ArrayList<>();
        for (Course course : courses) {
            assignments.addAll(courseService.getAllAssignments(course.getCourseId()));
        }
        return assignments;
    }

    @DeleteMapping("/student/{userName}/courses/{courseId}")
    public ResponseEntity<Map<String, String>> leaveCourse(
            @PathVariable String userName,
            @PathVariable String courseId) {
        
        Map<String, String> response = new HashMap<>();

        boolean isRemoved = studentService.removeSelfFromCourse(userName, courseId);
        if (isRemoved) {
            response.put("message", "Successfully removed from the course.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to remove from the course. Either the student or course was not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

