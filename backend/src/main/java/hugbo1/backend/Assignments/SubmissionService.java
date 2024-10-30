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
        if (!assignmentGrades.isEmpty()) {
            for (Double assignmentGrade : assignmentGrades) {
                averageGrade += assignmentGrade;
            }
            return averageGrade / assignmentGrades.size();
        }else {
            return 0; //Testing purposes
        }

    }
    public List<Double> getAllAssignmentGrades(int assignmentId) {
        return submissionRepository.getAllAssignmentGradesById(assignmentId);
    }
}
