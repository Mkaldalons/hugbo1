package hugbo1.backend.Users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody SignupRequest signupRequest) {
        if (userService.doesUserExistByEmail(signupRequest.getEmail())) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "Email already registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "Passwords do not match");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }else {
            User newUser = new User(
                    signupRequest.getUsername(),
                    signupRequest.getName(),
                    signupRequest.getEmail(),
                    signupRequest.getPassword(),
                    signupRequest.isInstructor()
            );
            userService.addUser(newUser);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "User registered successfully");
            return ResponseEntity.ok(responseBody);
        }
    }
}