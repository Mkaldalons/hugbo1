package hugbo1.backend.Assignments;

import jakarta.persistence.*;

@Entity
@Table(name = "Assignment_Submissions")
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submissionId;
    private int assignmentId;
    private double assignmentGrade;
    private int studentId;


    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public double getAssignmentGrade() {
        return assignmentGrade;
    }

    public void setAssignmentGrade(double assignmentGrade) {
        this.assignmentGrade = assignmentGrade;
    }
}
