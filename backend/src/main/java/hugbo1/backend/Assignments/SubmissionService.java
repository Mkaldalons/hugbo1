package hugbo1.backend.Assignments;

import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final StudentRepository studentRepository;

    public SubmissionService(SubmissionRepository submissionRepository, StudentRepository studentRepository) {
        this.submissionRepository = submissionRepository;
        this.studentRepository = studentRepository;
    }

    public double getAverageGradeFromId(int assignmentId) {
        List<Double> assignmentGrades = submissionRepository.getMaxGradeOfAssignmentForStudent(assignmentId);
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
    public boolean submittedByStudent(int assignmentId, int studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        List<AssignmentSubmission> submissions = submissionRepository.findByAssignmentIdAndStudent(assignmentId, student);
        return !submissions.isEmpty();
    }
    public List<Double> assignmentSubmissionByStudent(int assignmentId, Student student) {
        List<AssignmentSubmission> submissions = submissionRepository.findByAssignmentIdAndStudent(assignmentId, student);
        List<Double> grades = new ArrayList<>();
        for (AssignmentSubmission submission : submissions) {
            grades.add(submission.getAssignmentGrade());
        }
        return grades;
    }
}
