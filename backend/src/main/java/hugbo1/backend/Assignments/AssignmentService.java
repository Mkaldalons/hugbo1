package hugbo1.backend.Assignments;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private LocalDate dateToday = LocalDate.now();

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
    public boolean isPublished(int id) {
        Assignment assignment = assignmentRepository.findByAssignmentId(id);
        return assignment.isPublished();
    }
    public boolean canBeUnpublished(int id) {
        Assignment assignment = assignmentRepository.findByAssignmentId(id);
        LocalDate oneWeekFromNow = dateToday.plusWeeks(1);
        if (assignment.getDueDate().isAfter(dateToday) &&
                (assignment.getDueDate().isEqual(oneWeekFromNow)) ||
                assignment.getDueDate().isBefore(oneWeekFromNow) ) {
            return false;
        }
        return true;
    }
}
