package hugbo1.backend.Students;

import hugbo1.backend.Assignments.AssignmentSubmission;
import hugbo1.backend.Courses.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {


    Student findByStudentId(int id);
    Student findByUserName(String userName);

    @Query("SELECT s.courses FROM Student s WHERE s.studentId = :studentId")
    List<Course> findCourseByStudentId(@Param("studentId") int studentId);

    @Query("SELECT s.submissions FROM Student s WHERE s.studentId = :studentId")
    List<AssignmentSubmission> findAssignmentSubmissionByStudentId(@Param("studentId") int studentId);
}
