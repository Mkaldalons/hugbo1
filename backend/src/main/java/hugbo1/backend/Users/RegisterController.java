package hugbo1.backend.Users;

import hugbo1.backend.Students.Student;
import hugbo1.backend.Students.StudentService;
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
    public ResponseEntity<User> signUp(@RequestBody SignupRequest signupRequest) {
        if (userService.doesUserExistByEmail(signupRequest.getEmail())) {
            System.out.println("Email already exists");
            return ResponseEntity.notFound().build();
        }
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            System.out.println("Passwords do not match");
            return ResponseEntity.notFound().build();
        }
        User newUser = new User(
                signupRequest.getUsername(),
                signupRequest.getName(),
                signupRequest.getEmail(),
                signupRequest.getPassword(),
                signupRequest.getisInstructor()
        );
        userService.addUser(newUser);
        if (!newUser.isInstructor()){
            Student student = new Student();
            student.setUserName(newUser.getUserName());
            student.setName(newUser.getName());
            studentService.addStudent(student);
        }
        System.out.println("New user created");
        return ResponseEntity.ok(newUser);
    }

}