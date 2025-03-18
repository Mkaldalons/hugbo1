package hugbo1.backend.Assignments;

import com.fasterxml.jackson.core.type.TypeReference;
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
            jsonQuestions = objectMapper.writeValueAsString(assignmentRequest.getQuestionRequest());
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
    public ResponseEntity<AssignmentRequest> getAssignmentById(@PathVariable int assignmentId) {
        if (assignmentService.doesAssignmentExist(assignmentId)) {
            Assignment assignment = assignmentService.getAssignmentById(assignmentId);
            AssignmentRequest assignmentRequest = new AssignmentRequest();
            assignmentRequest.setDueDate(assignment.getDueDate());

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<QuestionRequest> questionRequests = objectMapper.readValue(assignment.getJsonData(), new TypeReference<List<QuestionRequest>>() {});
                assignmentRequest.setQuestionRequest(questionRequests);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assignmentRequest.setAssignmentName(assignment.getAssignmentName());
            assignmentRequest.setCourseId(assignment.getCourseId());
            assignmentRequest.setPublished(assignment.isPublished());
            return ResponseEntity.ok(assignmentRequest);
        }else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("courses/{courseId}")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentsByCourse(@PathVariable Integer courseId) {
        List<Assignment> assignments = new ArrayList<>();
        HashMap<String, List<AssignmentResponse>> response = new HashMap<>();
        List<AssignmentResponse> assignmentResponses = new ArrayList<>();
        if(courseService.doesCourseExist(courseId)) {
            assignments = assignmentService.getAllAssignmentsByCourseId(courseId);
            for(Assignment assignment : assignments) {
                AssignmentResponse assignmentResponse = new AssignmentResponse();
                assignmentResponse.setAssignmentId(assignment.getAssignmentId());
                assignmentResponse.setDueDate(assignment.getDueDate());
                assignmentResponse.setAssignmentName(assignment.getAssignmentName());
                assignmentResponse.setCourseId(assignment.getCourseId());
                assignmentResponse.setPublished(assignment.isPublished());
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<QuestionRequest> questionRequests = objectMapper.readValue(assignment.getJsonData(), new TypeReference<List<QuestionRequest>>() {});
                    assignmentResponse.setQuestionRequest(questionRequests);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                assignmentResponses.add(assignmentResponse);
            }
            return ResponseEntity.ok(assignmentResponses);
        }
        return ResponseEntity.status(404).body(assignmentResponses);
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
        if(!assignment.getJsonData().isEmpty()) {
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
