package hugbo1.backend.Assignments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    Assignment findById(int id);
    void deleteById(int id);
}
