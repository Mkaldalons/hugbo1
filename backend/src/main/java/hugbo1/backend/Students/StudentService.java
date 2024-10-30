package hugbo1.backend.Students;

import hugbo1.backend.Assignments.*;
import hugbo1.backend.Courses.Course;
import jakarta.persistence.Table;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentService assignmentService;

    public StudentService(StudentRepository studentRepository, AssignmentRepository assignmentRepository, AssignmentService assignmentService) {
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;
        this.assignmentService = assignmentService;
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }
    public Student getStudentByUserName(String name) {
        return studentRepository.findByUserName(name);
    }
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public boolean hasStudentSubmitted(Student student, int assignmentId) {
        List<AssignmentSubmission> assignmentSubmissions = studentRepository.findAssignmentSubmissionByStudentId(student.getStudentId());
        for (AssignmentSubmission assignmentSubmission : assignmentSubmissions) {
            if (assignmentSubmission.getAssignmentId() == assignmentId) {
                return true;
            }
        }
        return false;
    }

    public List<Assignment> getAllSubmissionsByStudent(Student student) {
        List<AssignmentSubmission> assignmentSubmissions = studentRepository.findAssignmentSubmissionByStudentId(student.getStudentId());
        List<Assignment> assignments = assignmentService.getAllAssignments();
        List<Assignment> submissions = new ArrayList<>();
        List<Integer> submittedAssignmentIds = new ArrayList<>();
        List<Integer> allAssignmentIds = new ArrayList<>();
        for (AssignmentSubmission assignmentSubmission : assignmentSubmissions) {
            submittedAssignmentIds.add(assignmentSubmission.getAssignmentId());
        }
        for (Assignment assignment : assignments) {
            allAssignmentIds.add(assignment.getAssignmentId());
        }
        submittedAssignmentIds.sort(Integer::compareTo);
        allAssignmentIds.sort(Integer::compareTo);
        for (int i = 0; i < submittedAssignmentIds.size(); i++) {
            if (Objects.equals(submittedAssignmentIds.get(i), allAssignmentIds.get(i))) {
                submissions.add(assignments.get(i));
            }
        }
        return submissions;
    }

    public double getGradeForAssignment(Student student, int assignmentId) {
        List<AssignmentSubmission> assignmentSubmissions = studentRepository.findAssignmentSubmissionByStudentId(student.getStudentId());
        for (AssignmentSubmission assignmentSubmission : assignmentSubmissions) {
            if (assignmentId == assignmentSubmission.getAssignmentId()) {
                return assignmentSubmission.getAssignmentGrade();
            }
        }
        return 0;
    }
}
