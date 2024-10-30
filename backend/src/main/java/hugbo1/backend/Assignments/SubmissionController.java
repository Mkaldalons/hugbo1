package hugbo1.backend.Assignments;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping("/average-grade/{assignmentId}")
    public double averageGrade(@PathVariable int assignmentId) {
        return submissionService.getAverageGradeFromId(assignmentId);
    }


    public void processJson(){

    }

}
