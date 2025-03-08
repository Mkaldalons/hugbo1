package hugbo1.backend.Assignments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    Assignment findByAssignmentId(int id);
    boolean existsByAssignmentId(int id);
    void deleteByAssignmentId(int id);

    @Query("SELECT a FROM Assignment a WHERE a.courseId = :courseId")
    List<Assignment> findAssignmentsByCourseId(@Param("courseId") Integer courseId);

    @Query("""
    SELECT a.assignmentId, a.dueDate, sub.student.id, sub.assignmentGrade
    FROM Assignment a
    LEFT JOIN AssignmentSubmission sub ON a.assignmentId = sub.assignmentId
    WHERE a.courseId = :courseId
""")
List<Object[]> findAssignmentsAndSubmissionsByCourseId(@Param("courseId") Integer courseId);

}
