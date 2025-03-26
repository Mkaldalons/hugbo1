package hugbo1.backend.Students;

import hugbo1.backend.Assignments.*;
import hugbo1.backend.Courses.Course;
import hugbo1.backend.Courses.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AssignmentService assignmentService;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, AssignmentService assignmentService, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.assignmentService = assignmentService;
        this.courseRepository = courseRepository;
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
        return -1;
    }
    public double getAverageGradeForStudent(Student student) {
        List <AssignmentSubmission> submissions = studentRepository.findAssignmentSubmissionByStudentId(student.getStudentId());
        double averageGrade = 0;
        for (AssignmentSubmission assignmentSubmission : submissions) {
            System.out.println("Assignment Id: " + assignmentSubmission.getAssignmentId() + "grade: "+assignmentSubmission.getAssignmentGrade());
           averageGrade += assignmentSubmission.getAssignmentGrade();
        }
        if (!submissions.isEmpty()) {
            return averageGrade/submissions.size();
        }else {
            return -1;
        }
    }
    public double getAverageFromCourse(Course course, Student student) {
        List<Assignment> courseAssignments = assignmentService.getAllPublishedAssignmentByCourseId(course.getCourseId());
        Map<Integer, Assignment> courseAssignmentsMap = courseAssignments.stream()
                .collect(Collectors.toMap(Assignment::getAssignmentId, assignment -> assignment));

        List<AssignmentSubmission> allSubmissions = studentRepository.findAssignmentSubmissionByStudentId(student.getStudentId());

        List<AssignmentSubmission> submissions = allSubmissions.stream()
                .filter(submission -> courseAssignmentsMap.containsKey(submission.getAssignmentId()))
                .collect(Collectors.toList());

        double totalGrade = submissions.stream()
                .mapToDouble(AssignmentSubmission::getAssignmentGrade)
                .sum();
        return submissions.isEmpty() ? -1 : totalGrade / submissions.size();
    }
    public List<Course> getAllCoursesForStudent(Student student) {
        return courseRepository.findCoursesByStudentId(student.getStudentId());
    }

    public boolean removeSelfFromCourse(String userName, String courseId) {
        Student student = studentRepository.findByUserName(userName);
        if (student == null) {
            System.out.println("Student not found: " + userName);
            return false;
        }
    
        Optional<Course> courseOpt = student.getCourses().stream()
            .filter(course -> course.getCourseId().equals(courseId))
            .findFirst();
    
        if (!courseOpt.isPresent()) {
            System.out.println("Course not found for student: " + courseId);
            return false;
        }
    
        Course course = courseOpt.get();
        student.getCourses().remove(course);
        studentRepository.save(student);
        return true;
    }
}
