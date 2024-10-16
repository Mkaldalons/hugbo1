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

    public static class AssignmentRequest {
        private String instructorUsername;
        private String courseName;
        private String assignmentName;
        private Date publishDate;
        private Date dueDate;

        // Getters and Setters
        public String getInstructorUsername() {
            return instructorUsername;
        }

        public void setInstructorUsername(String instructorUsername) {
            this.instructorUsername = instructorUsername;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getAssignmentName() {
            return assignmentName;
        }

        public void setAssignmentName(String assignmentName) {
            this.assignmentName = assignmentName;
        }

        public Date getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(Date publishDate) {
            this.publishDate = publishDate;
        }

        public Date getDueDate() {
            return dueDate;
        }

        public void setDueDate(Date dueDate) {
            this.dueDate = dueDate;
        }
    }


    private final AssignmentRepository assignmentRepository = new AssignmentRepository();
    private final UserRepository userRepository = new UserRepository();

    @PostMapping("/create")
    public ResponseEntity<String> createAssignment(@RequestBody AssignmentRequest assignmentRequest) {

        // Fetch the instructor by username
        Instructor instructor = userRepository.getInstructorByUsername(assignmentRequest.getInstructorUsername());
        if (instructor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Instructor not found");
        }

        // Check if the instructor is teaching the course
        Course course = new Course(assignmentRequest.getCourseName()); // Assume a basic constructor for Course
        if (!instructor.getCourses().contains(course)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Instructor not teaching this course");
        }

        // Create the assignment using the properties from the request body
        String assignmentName = assignmentRequest.getAssignmentName();
        Date publishDate = assignmentRequest.getPublishDate();
        Date dueDate = assignmentRequest.getDueDate();

        // Assuming the Instructor class has a createAssignment method
        instructor.createAssignment(course, assignmentName, publishDate, dueDate);

        // Respond with a success message
        return ResponseEntity.ok("Assignment created successfully");
    }
}
