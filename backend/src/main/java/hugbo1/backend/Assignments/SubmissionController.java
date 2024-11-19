package hugbo1.backend.Assignments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SubmissionController {

    private final SubmissionService submissionService;
    private final StudentService studentService;
    private final AssignmentService assignmentService;
    private final SubmissionRepository submissionRepository;

    public SubmissionController(SubmissionService submissionService, StudentService studentService, AssignmentService assignmentService, SubmissionRepository submissionRepository) {
        this.submissionService = submissionService;
        this.studentService = studentService;
        this.assignmentService = assignmentService;
        this.submissionRepository = submissionRepository;
    }

    @GetMapping("/average-grade/{assignmentId}")
    public double averageGrade(@PathVariable int assignmentId) {
        double value = submissionService.getAverageGradeFromId(assignmentId);
        return Math.round(value * 100.0) / 100.0;
    }
    @GetMapping("/grades/{assignmentId}")
    public List<Double> grades(@PathVariable int assignmentId) {
        return submissionService.getAllAssignmentGrades(assignmentId);
    }
    @GetMapping("/assignment-submission/{assignmentId}")
    public boolean hasBeenSubmitted(@PathVariable int assignmentId, @RequestParam String userName) {
        System.out.println(userName+" and assignment id: "+assignmentId);
        Student student = studentService.getStudentByUserName(userName);
        return submissionService.submittedByStudent(assignmentId, student.getStudentId());
    }

    @GetMapping("/assignment-submissions/{assignmentId}")
    public List<Double> submissions(@PathVariable int assignmentId, @RequestParam String userName) {
        Student student = studentService.getStudentByUserName(userName);
        return submissionService.assignmentSubmissionByStudent(assignmentId, student);
    }

    @PostMapping("/submit-assignment")
    public ResponseEntity<Map<String, Object>> submitAssignment(@RequestBody SubmissionRequest submissionRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Assignment assignment = assignmentService.getAssignmentById(submissionRequest.getAssignmentId());
            Student student = studentService.getStudentByUserName(submissionRequest.getUserName());

            String jsonData = assignment.getJsonData();
            ObjectMapper objectMapper = new ObjectMapper();
            List<QuestionRequest> questions = objectMapper.readValue(jsonData, new TypeReference<>() {
            });

            List<String> studentAnswers = submissionRequest.getAnswers();

            if (questions != null && studentAnswers != null) {
                double correctCount = 0;

                for (int i = 0; i < questions.size(); i++) {
                    if (i < studentAnswers.size()) {
                        String correctAnswer = questions.get(i).getCorrectAnswer();
                        if (studentAnswers.get(i).equals(correctAnswer)) {
                            correctCount++;
                        }
                    }
                }
                AssignmentSubmission submission = new AssignmentSubmission();
                submission.setStudent(student);
                submission.setAssignmentId(submissionRequest.getAssignmentId());
                submission.setAssignmentGrade((correctCount / questions.size())*10);
                submissionRepository.save(submission);
                response.put("grade", submission.getAssignmentGrade());
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing submission: " + e.getMessage(), e);
        }
        response.put("grade", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
