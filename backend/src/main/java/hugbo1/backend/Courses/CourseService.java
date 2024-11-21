package hugbo1.backend.Courses;

import hugbo1.backend.Assignments.Assignment;
import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentRepository;
import hugbo1.backend.Assignments.AssignmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
  

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(String id) {
        return courseRepository.findById(id);
    }

    public boolean doesCourseExist(String id) {
        return courseRepository.existsById(id);
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    public List<Student> getAllStudents(String courseId) {
        return courseRepository.findStudentsByCourseId(courseId);
    }
    
    public List<Map<String, Object>> calculateStudentGrades(String courseId, List<Student> students) {
        List<Object[]> assignmentsAndSubmissions = assignmentRepository.findAssignmentsAndSubmissionsByCourseId(courseId);
    
        Map<Integer, List<Double>> studentGradesMap = new HashMap<>();
        Map<Integer, Integer> studentSubmissionCountMap = new HashMap<>();
    
        for (Object[] row : assignmentsAndSubmissions) {
            LocalDate dueDate = (LocalDate) row[1];
            Integer studentId = (Integer) row[2];
            Double assignmentGrade = (Double) row[3];
    
            if (assignmentGrade == null && dueDate.isBefore(LocalDate.now())) {
                assignmentGrade = 0.0;
            }
    
            if (studentId != null) {
                studentGradesMap.computeIfAbsent(studentId, k -> new ArrayList<>()).add(assignmentGrade);
    
                studentSubmissionCountMap.put(studentId, studentSubmissionCountMap.getOrDefault(studentId, 0) + 1);
            }
        }
    
        return students.stream()
                .map(student -> {
                    Integer studentId = student.getStudentId();
                    List<Double> grades = studentGradesMap.getOrDefault(studentId, Collections.emptyList());
                    double averageGrade = grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                    int submissionCount = studentSubmissionCountMap.getOrDefault(studentId, 0); 
    
                    Map<String, Object> result = new HashMap<>();
                    result.put("studentId", studentId);
                    result.put("name", student.getName());
                    result.put("userName", student.getUserName());
                    result.put("averageGrade", averageGrade);
                    result.put("submissionCount", submissionCount); 
                    return result;
                })
                .toList();
    }

    


    public void registerStudentToCourse(Student student, Course course) {
        course.getStudents().add(student);
        student.getCourses().add(course);

        courseRepository.save(course);
    }
    public List<Course> getAllCoursesByInstructor(String userName) {
        return courseRepository.findByInstructor(userName);
    }
    public List<Assignment> getAllAssignments(String courseId){
        return assignmentRepository.findAssignmentsByCourseId(courseId);
    }

    public boolean removeStudentFromCourse(String courseId, String studentId) {
        Optional<Course> courseOpt = getCourseById(courseId);
    
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            
            Optional<Student> studentOpt = course.getStudents().stream()
                .filter(student -> student.getStudentId() == Integer.parseInt(studentId))
                .findFirst();
    
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                
                course.getStudents().remove(student);
                student.getCourses().remove(course);
                
                courseRepository.save(course);
                studentRepository.save(student);
                
                return true;
            }
        }
        return false;
    }
    
    public List<Student> findStudentsByCriteria(String courseId, String name, String username) {
        Optional<Course> courseOpt = getCourseById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            
            List<Student> students = new ArrayList<>(course.getStudents());
            
            return students.stream()
                    .filter(student -> (name == null || student.getName().equalsIgnoreCase(name)) &&
                                    (username == null || student.getUserName().equalsIgnoreCase(username)))
                    .toList();
        }
        return new ArrayList<>();
    }

    public List<Course> getAllCoursesByStudentId(int studentId) {
        return courseRepository.findCoursesByStudentId(studentId);
    }

    public void updateCourseName(String courseId, String newCourseName) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setCourseName(newCourseName);
            courseRepository.save(course);
        } else {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found");
        }
    }
}
