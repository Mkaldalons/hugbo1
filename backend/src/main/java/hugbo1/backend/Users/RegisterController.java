package hugbo1.backend.Users;

import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {
    private final UserService userService;
    private final StudentService studentService;

    public RegisterController(UserService userService, StudentService studentService) {
        this.userService = userService;
        this.studentService = studentService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupRequest signupRequest) {
        if (userService.doesUserExistByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already registered");
        }
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }

        // Create a new user and add to the user store
        User newUser = new User(
                signupRequest.getUsername(),
                signupRequest.getName(),
                signupRequest.getEmail(),
                signupRequest.getPassword(),
                signupRequest.isInstructor()
        );
        userService.addUser(newUser);
        if (!newUser.isInstructor()){
            Student student = new Student();
            student.setUserName(newUser.getUserName());
            student.setName(newUser.getName());
            studentService.addStudent(student);
        }
        return ResponseEntity.ok("User registered successfully");
    }

}