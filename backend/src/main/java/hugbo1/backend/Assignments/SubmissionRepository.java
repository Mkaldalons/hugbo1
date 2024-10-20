package hugbo1.backend.Assignments;

import hugbo1.backend.Students.Student;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(name="Assignment_Submissions")
public interface SubmissionRepository extends JpaRepository<Student, Integer> {

}
