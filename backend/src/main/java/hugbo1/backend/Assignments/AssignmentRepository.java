package hugbo1.backend.Assignments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    Assignment findByAssignmentId(int id);
    boolean existsByAssignmentId(int id);
    void deleteByAssignmentId(int id);
}
