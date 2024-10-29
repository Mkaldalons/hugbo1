package hugbo1.backend.Assignments;

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

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
    public Assignment getAssignmentById(int id) {
        return assignmentRepository.findById(id);
    }
    public void deleteAssignmentById(int id) {
        assignmentRepository.deleteById(id);
    }
}
