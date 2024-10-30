package hugbo1.backend.Assignments;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public void createAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }
    public void updateAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }
    public boolean doesAssignmentExist(int id) {
        return assignmentRepository.existsByAssignmentId(id);
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentById(int id) {
        return assignmentRepository.findByAssignmentId(id);
    }

    @Transactional
    public void deleteAssignmentById(int id) {
        assignmentRepository.deleteByAssignmentId(id);
    }
}
