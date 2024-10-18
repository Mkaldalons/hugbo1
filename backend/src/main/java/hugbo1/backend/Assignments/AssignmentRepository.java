package hugbo1.backend.Assignments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.*;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {

    Assignment findByName(String name);
    Assignment findById(int id);
    void deleteByName(String name);
    void deleteById(int id);
}
