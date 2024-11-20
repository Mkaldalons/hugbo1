package hugbo1.backend.Students;

import hugbo1.backend.Assignments.Assignment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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

