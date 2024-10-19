package hugbo1.backend.Assignments;

import hugbo1.backend.Courses.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/assignments")
@CrossOrigin(origins = "http://localhost:3000")
public class AssignmentController {

    private final AssignmentService assignmentService;
    //private final CourseService courseService;

    public AssignmentController(AssignmentService assignmentService, CourseService courseService) {
        this.assignmentService = assignmentService;
        //this.courseService = courseService;
    }

    @PostMapping("/assignments")
    public ResponseEntity<Map<String, Object>> createAssignment(@RequestBody AssignmentRequest assignmentRequest) throws IOException {
//        if(!courseService.doesCourseExist(assignmentRequest.getCourseId())){
//            HashMap<String, Object> response = new HashMap<>();
//            response.put("message", "Course does not exist");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        } // Vantar að velja course í framendanum
        Assignment assignment = new Assignment();
        assignment.setAssignmentName("New Assignment");
        assignment.setCourseId("missing101");
        assignment.setDate(assignmentRequest.getDueDate());
        String jsonData = new String(assignmentRequest.getJsonData().getBytes());
        assignment.setJsonData(jsonData);
        assignmentService.createAssignment(assignment);
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Assignment created");
        return ResponseEntity.ok(response);
    }
}
