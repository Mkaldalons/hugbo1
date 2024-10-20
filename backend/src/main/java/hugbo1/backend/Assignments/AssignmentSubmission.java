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


}
