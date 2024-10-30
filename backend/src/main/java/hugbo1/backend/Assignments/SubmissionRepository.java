package hugbo1.backend.Assignments;

import hugbo1.backend.Students.Student;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Table(name="Assignment_Submissions")
public interface SubmissionRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s.assignmentGrade FROM AssignmentSubmission s WHERE s.assignmentId = :assignmentId")
    List<Double> getAllAssignmentGradesById(@Param("assignmentId") int assignmentId);

}
