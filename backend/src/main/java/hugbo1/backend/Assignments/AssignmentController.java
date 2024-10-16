package hugbo1.backend.Assignments;

import hugbo1.backend.Users.Instructor;
import hugbo1.backend.Users.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/assignments")
@CrossOrigin(origins = "http://localhost:3000")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository = new AssignmentRepository();
    private final UserRepository userRepository = new UserRepository();

    @PostMapping("/create")
    public ResponseEntity<String> createAssignment(
            @RequestParam String instructorUsername,
            @RequestParam String courseName,
            @RequestParam String assignmentName,
            @RequestParam Date publishDate,
            @RequestParam Date dueDate) {

        // Fetch the instructor by username
        Instructor instructor = userRepository.getInstructorByUsername(instructorUsername);
        if (instructor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Instructor not found");
        }

        // Check if the instructor is teaching the course
        Course course = new Course(courseName); // Assume a basic constructor for Course
        if (!instructor.getCourses().contains(course)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Instructor not teaching this course");
        }

        // Create the assignment
        instructor.createAssignment(course, assignmentName, publishDate, dueDate);

        return ResponseEntity.ok("Assignment created successfully");
    }
}
