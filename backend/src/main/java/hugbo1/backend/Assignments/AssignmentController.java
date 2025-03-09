package hugbo1.backend.Assignments;

import com.fasterxml.jackson.databind.ObjectMapper;
import hugbo1.backend.Courses.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final CourseService courseService;

    public AssignmentController(AssignmentService assignmentService, CourseService courseService) {
        this.assignmentService = assignmentService;
        this.courseService = courseService;
    }

    @PostMapping("")
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
        assignment.setAssignmentName(assignmentRequest.getAssignmentName());
        assignment.setDueDate(assignmentRequest.getDueDate());
        assignment.setJsonData(jsonQuestions);
        assignmentService.createAssignment(assignment);
        response.put("AssignmentId", assignment.getAssignmentId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable int assignmentId) {
        if (assignmentService.doesAssignmentExist(assignmentId)) {
            return ResponseEntity.ok(assignmentService.getAssignmentById(assignmentId));
        }else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("courses/{courseId}")
    public ResponseEntity<Map<String, List<Assignment>>> getAssignmentsByCourse(@PathVariable Integer courseId) {
        List<Assignment> assignments = new ArrayList<>();
        HashMap<String, List<Assignment>> response = new HashMap<>();
        if(courseService.doesCourseExist(courseId)) {
            assignments = assignmentService.getAllAssignmentsByCourseId(courseId);
            response.put("", assignments);
            return ResponseEntity.ok(response);
        }
        response.put("Course with id: "+courseId+" does not exist.", assignments);
        return ResponseEntity.status(404).body(response);
    }

    @PatchMapping("{assignmentId}")
    public ResponseEntity<Map<String, Object>> editAssignment(@RequestBody AssignmentRequest assignmentRequest, @PathVariable int assignmentId) {
        Map<String, Object> response = new HashMap<>();
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        if(assignmentRequest.getDueDate() != null) {
            assignment.setDueDate(assignmentRequest.getDueDate());
        }
        if(assignmentRequest.getAssignmentName() != null) {
            assignment.setAssignmentName(assignmentRequest.getAssignmentName());
        }
        if(assignmentRequest.getCourseId() != null) {
            assignment.setCourseId(assignmentRequest.getCourseId());
        }
        if(assignment.getJsonData().isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonQuestions;
            try {
                jsonQuestions = objectMapper.writeValueAsString(assignmentRequest.getQuestionRequest());
            } catch (Exception e) {
                response.put("message", "Couldn't serialize to JSON");
                return ResponseEntity.status(500).body(response);
            }
            assignment.setJsonData(jsonQuestions);
        }
        if(assignmentRequest.getPublished() != null) {
            if (assignmentRequest.getPublished()) {
                assignment.setPublished(assignmentRequest.getPublished());
            }
            else
            {
                if(assignmentService.canBeUnpublished(assignmentId))
                {
                    assignment.setPublished(assignmentRequest.getPublished());
                }
                else {
                    response.put("message", "Assignment cannot be unpublished because students are already working on it.");
                }
            }
            assignment.setPublished(assignmentRequest.getPublished());
        }
        assignmentService.updateAssignment(assignment);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{assignmentId}")
    public ResponseEntity<Map<String, Object>> deleteAssignment(@PathVariable int assignmentId) {
        Map<String, Object> response = new HashMap<>();
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
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
