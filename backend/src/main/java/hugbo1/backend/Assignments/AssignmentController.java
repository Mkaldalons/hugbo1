package hugbo1.backend.Assignments;

import com.fasterxml.jackson.databind.ObjectMapper;
import hugbo1.backend.Courses.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final CourseService courseService;

    public AssignmentController(AssignmentService assignmentService, CourseService courseService) {
        this.assignmentService = assignmentService;
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("Assignment name is passed in as: "+assignmentRequest.getAssignmentName());

        if (!courseService.doesCourseExist(assignmentRequest.getCourseId())) {
            System.out.println("Course not found");
            response.put("message", "Course not found");
            return ResponseEntity.status(400).body(response);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonQuestions;
        try {
            jsonQuestions = objectMapper.writeValueAsString(assignmentRequest.getQuestionRequests());
        } catch (Exception e) {
            response.put("message", "Couldn't serialize to JSON");
            return ResponseEntity.status(500).body(response);
        }
        Assignment assignment = new Assignment();
        assignment.setCourseId(assignmentRequest.getCourseId());
        assignment.setAssignmentName(assignmentRequest.getAssignmentName());
        assignment.setDueDate(assignmentRequest.getDueDate());
        assignment.setJsonData(jsonQuestions);
        assignmentService.createAssignment(assignment);
        System.out.println("Assignment being created");
        response.put("message", "Assignment created");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/assignments")
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable int assignmentId) {
        if (assignmentService.doesAssignmentExist(assignmentId)) {
            return ResponseEntity.ok(assignmentService.getAssignmentById(assignmentId));
        }else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/assignments/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable Integer courseId) {
        if (courseService.doesCourseExist(courseId)) {
            if (!assignmentService.getAllPublishedAssignmentByCourseId(courseId).isEmpty()) {
                return ResponseEntity.ok(assignmentService.getAllPublishedAssignmentByCourseId(courseId));
            }
            System.out.println("There are no published assignments for this course yet");
            return ResponseEntity.status(404).body(null);
        }
        System.out.println("Course does not exist");
        return ResponseEntity.status(404).body(null);
    }

    @PostMapping("/edit")
    public ResponseEntity<Map<String, Object>> editAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        Map<String, Object> response = new HashMap<>();
        if (!courseService.doesCourseExist(assignmentRequest.getCourseId())) {
            response.put("message", "Course not found");
            return ResponseEntity.status(400).body(response);
        }else {
            Assignment assignment = assignmentService.getAssignmentById(assignmentRequest.getAssignmentId());
            assignment.setDueDate(assignmentRequest.getDueDate());
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonQuestions;
            try {
                jsonQuestions = objectMapper.writeValueAsString(assignmentRequest.getQuestionRequest());
            } catch (Exception e) {
                response.put("message", "Couldn't serialize to JSON");
                return ResponseEntity.status(500).body(response);
            }
            assignment.setJsonData(jsonQuestions);
            assignment.setAssignmentName(assignmentRequest.getAssignmentName());
            assignmentService.updateAssignment(assignment);
            response.put("message", "Assignment updated");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/delete-assignment")
    public ResponseEntity<Map<String, Object>> deleteAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        Map<String, Object> response = new HashMap<>();
        Assignment assignment = assignmentService.getAssignmentById(assignmentRequest.getAssignmentId());
        if (assignmentService.doesAssignmentExist(assignment.getAssignmentId())) {
            assignmentService.deleteAssignmentById(assignment.getAssignmentId());
            response.put("message", "Assignment deleted");
            return ResponseEntity.ok(response);
        }else{
            response.put("message", "Assignment not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PostMapping("/publish-assignment/{assignmentId}")
    public ResponseEntity<Map<String, Object>> publishAssignment(@PathVariable int assignmentId) {
        Map<String, Object> response = new HashMap<>();
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        if (assignmentService.doesAssignmentExist(assignment.getAssignmentId())) {
            assignment.setPublished(true);
            assignmentService.updateAssignment(assignment);
            response.put("message", "Assignment published");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Assignment not found");
        return ResponseEntity.status(404).body(response);
    }

    @PostMapping("/unpublish-assignment/{assignmentId}")
    public ResponseEntity<Map<String, Object>> unpublishAssignment(@PathVariable int assignmentId) {
        Map<String, Object> response = new HashMap<>();

        // Retrieve the assignment
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        if (assignment == null) {
            response.put("message", "Assignment not found");
            return ResponseEntity.status(404).body(response);
        }

        // Check if assignment can be unpublished
        if (!assignmentService.canBeUnpublished(assignment.getAssignmentId())) {
            response.put("message", "Assignment cannot be unpublished because students are already working on it.");
            return ResponseEntity.ok(response);
        }

        // Proceed with unpublishing the assignment
        assignment.setPublished(false);
        assignmentService.updateAssignment(assignment);
        response.put("message", "Assignment successfully unpublished");
        return ResponseEntity.ok(response);
    }
}
