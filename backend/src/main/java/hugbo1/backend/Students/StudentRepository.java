package hugbo1.backend.Students;

import hugbo1.backend.Assignments.AssignmentSubmission;
import hugbo1.backend.Courses.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByStudentId(int id);
    Student findByUserName(String userName);

    @Query("SELECT s.courses FROM Student s WHERE s.studentId = :studentId")
    List<Course> findCourseByStudentId(@Param("studentId") int studentId);

    @Query("SELECT s.submissions FROM Student s WHERE s.studentId = :studentId")
    List<AssignmentSubmission> findAssignmentSubmissionByStudentId(@Param("studentId") int studentId);

    @Query("SELECT CASE WHEN COUNT(sc) > 0 THEN TRUE ELSE FALSE END " +
       "FROM Student s JOIN s.courses sc " +
       "WHERE s.userName = :userName AND sc.id = :courseId")
    boolean isStudentEnrolledInCourse(@Param("userName") String userName, @Param("courseId") String courseId);


}
