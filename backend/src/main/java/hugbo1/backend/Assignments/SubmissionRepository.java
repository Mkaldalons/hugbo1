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
public interface SubmissionRepository extends JpaRepository<AssignmentSubmission, Integer> {

    @Query("SELECT s.assignmentGrade FROM AssignmentSubmission s WHERE s.assignmentId = :assignmentId")
    List<Double> getAllAssignmentGradesById(@Param("assignmentId") int assignmentId);

    @Query("SELECT s.assignmentId FROM AssignmentSubmission s WHERE s.student = :student")
    List<Integer> getAllAssignmentIdsByStudent(@Param("student") Student student);

    @Query("SELECT MAX(s.assignmentGrade) " +
            "FROM AssignmentSubmission s " +
            "WHERE s.assignmentId = :assignmentId " +
            "GROUP BY s.student")
    List<Double> getMaxGradeOfAssignmentForStudent(@Param("assignmentId") int assignmentId);


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM AssignmentSubmission s " +
            "WHERE s.assignmentId = :assignmentId AND s.student = :student")
    boolean existsByAssignmentIdAndStudentId(@Param("assignmentId") int assignmentId, @Param("student") Student student);

    List<AssignmentSubmission> findByAssignmentIdAndStudent(int assignmentId, Student student);

    List<AssignmentSubmission> getAssignmentSubmissionByAssignmentId(int assignmentId);
}
