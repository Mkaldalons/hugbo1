package hugbo1.backend.Assignments;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;

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
        LocalDateTime dueDate = assignment.getDueDate();
        LocalDate oneWeekFromNow = dateToday.plusWeeks(1);

        return !dueDate.isBefore(oneWeekFromNow.atStartOfDay()) && !dueDate.isEqual(oneWeekFromNow.atStartOfDay());
    }
    public List<Assignment> getAllPublishedAssignmentByCourseId(Integer courseId) {
        List<Assignment> assignments = assignmentRepository.findAssignmentsByCourseId(courseId);
        assignments.removeIf(assignment -> !assignment.isPublished());
        return assignments;
    }

    public List<Assignment> getAllAssignmentsByCourseId(Integer courseId) {
        return assignmentRepository.findAssignmentsByCourseId(courseId);
    }
}
