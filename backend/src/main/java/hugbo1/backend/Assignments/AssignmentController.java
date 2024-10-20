package hugbo1.backend.Assignments;

import com.fasterxml.jackson.databind.ObjectMapper;
import hugbo1.backend.Courses.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    }
