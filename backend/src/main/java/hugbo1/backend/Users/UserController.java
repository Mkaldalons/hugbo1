
package hugbo1.backend.Users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update-password")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody UserRequest userRequest) {
        Map<String, Object> responseBody = new HashMap<>();

        User user = userService.getUserByUserName(userRequest.getUsername());

        if(userRequest.getOldPassword().equals(user.getPassword())){
            user.setPassword(userRequest.getNewPassword());
            userService.changePassword(user, user.getPassword());
            responseBody.put("status", "Password changed successfully");
            return ResponseEntity.ok(responseBody);
        } else {
            responseBody.put("status", "Wrong password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
    }
    @PostMapping("/delete-account")
    public ResponseEntity<Map<String, Object>> deleteAccount(@RequestBody UserRequest userRequest) {
        Map<String, Object> responseBody = new HashMap<>();
        User user = userService.getUserByUserName(userRequest.getUsername());
        if (userService.doesUserExistByEmail(user.getEmail())) {
            userService.deleteUser(user);
            responseBody.put("status", "Account deleted successfully");
            return ResponseEntity.ok(responseBody);
        }else {
            responseBody.put("status", "Wrong email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
    }
}

