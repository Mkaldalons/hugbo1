
package hugbo1.backend.Users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LogInController {

    private final UserService userService;

    public LogInController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity< Map <String, Object> > login(@RequestBody LoginRequest loginRequest) {
        if (userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())) {
            boolean isInstructor = userService.isInstructor(loginRequest.getUsername());
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("isInstructor", isInstructor);
            System.out.println("isInstructor: " + isInstructor);

            return ResponseEntity.ok(responseBody);
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }
}
