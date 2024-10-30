package hugbo1.backend.Students;

import hugbo1.backend.Assignments.Assignment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}

