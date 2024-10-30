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

        if (!courseService.doesCourseExist(assignmentRequest.getCourseId())) {
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
        assignment.setAssignmentName("Assignment");
        assignment.setDueDate(assignmentRequest.getDueDate());
        assignment.setJsonData(jsonQuestions);
        assignmentService.createAssignment(assignment);

        response.put("message", "Assignment created");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/assignments")
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        for (Assignment assignment : assignments) {
            System.out.println(assignment.getAssignmentName());
            System.out.println(assignment.getDueDate());
            System.out.println(assignment.getJsonData());
            System.out.println(assignment.getAssignmentId());
        }
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable int assignmentId) {
        System.out.println(assignmentId);
        if (assignmentService.doesAssignmentExist(assignmentId)) {
            System.out.println("Assignment found");
            return ResponseEntity.ok(assignmentService.getAssignmentById(assignmentId));
        }else {
            System.out.println("Assignment not found, id passed from frontend is: "+assignmentId);
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<Map<String, Object>> editAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        System.out.println("Assignment edited");
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
        System.out.println("Assignment with this id is being deleted: "+assignmentRequest.getAssignmentId());
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
}
