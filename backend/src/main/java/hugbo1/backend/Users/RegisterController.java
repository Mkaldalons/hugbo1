package hugbo1.backend.Users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {
    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupRequest signupRequest) {
        if (userRepository.doesUserExist(signupRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }

        // Create a new user without manually setting the ID
        User newUser = new User(
                null, // ID will be generated
                signupRequest.getName(),
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPassword()
        );
        userRepository.addUser(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
}
