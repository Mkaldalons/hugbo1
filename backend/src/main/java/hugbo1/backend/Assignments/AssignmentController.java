package hugbo1.backend.Assignments;

import hugbo1.backend.Users.Instructor;
import hugbo1.backend.Users.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/assignments")
@CrossOrigin(origins = "http://localhost:3000")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    //Spurning hvort við lesum inn spurningar og svör í sitthvoru fallinu og metadata?
    //Er smá confused
    @PostMapping("/lesa_inn_spurningar")
    public ResponseEntity<String> saveTextAsJson(@RequestBody AssignmentRequest assignmentRequest) throws IOException {
        return null;
    }


//    private final AssignmentRepository assignmentRepository = new AssignmentRepository();
//    private final UserRepository userRepository = new UserRepository();

//    @PostMapping("/create")
//    public ResponseEntity<String> createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
//
//        // Fetch the instructor by username
//        Instructor instructor = userRepository.getInstructorByUsername(assignmentRequest.getInstructorUsername());
//        if (instructor == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Instructor not found");
//        }
//
//        // Check if the instructor is teaching the course
//        Course course = new Course(assignmentRequest.getCourseName()); // Assume a basic constructor for Course
//        if (!instructor.getCourses().contains(course)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Instructor not teaching this course");
//        }
//
//        // Create the assignment using the properties from the request body
//        String assignmentName = assignmentRequest.getAssignmentName();
//        Date publishDate = assignmentRequest.getPublishDate();
//        Date dueDate = assignmentRequest.getDueDate();
//
//        // Assuming the Instructor class has a createAssignment method
//        instructor.createAssignment(course, assignmentName, publishDate, dueDate);
//
//        // Respond with a success message
//        return ResponseEntity.ok("Assignment created successfully");
//    }
}
