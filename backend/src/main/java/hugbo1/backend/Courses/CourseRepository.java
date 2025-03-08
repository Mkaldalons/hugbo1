package hugbo1.backend.Courses;

import hugbo1.backend.Students.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findByCourseName(String courseName);
    Course findByCourseId(Integer courseId);

    @Query("SELECT c.students FROM Course c WHERE c.courseId = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Integer courseId);

    @Query("SELECT s.courses FROM Student s WHERE s.studentId =:studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") int studentId);

    @Query("SELECT DISTINCT c FROM Course c WHERE c.instructor = :userName")
    List<Course> findByInstructor(@Param("userName") String userName);

}
