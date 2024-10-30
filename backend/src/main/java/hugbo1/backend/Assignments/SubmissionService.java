package hugbo1.backend.Assignments;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }
    public double getAverageGradeFromId(int assignmentId) {
        List<Double> assignmentGrades = submissionRepository.getAllAssignmentGradesById(assignmentId);
        double averageGrade = 0;
        for (Double assignmentGrade : assignmentGrades) {
            averageGrade += assignmentGrade;
        }
        return averageGrade / assignmentGrades.size();
    }
}
