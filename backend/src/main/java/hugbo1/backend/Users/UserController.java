
package hugbo1.backend.Users;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/linkToUserAccountStuff")
    public ResponseEntity<String> deleteAccount(@RequestBody UserRequest userRequest) {
        if (!userRequest.isIs_instructor()){
            if (userService.doesUserExistByEmail(userRequest.getEmail())){
                User user = userService.getUserByEmail(userRequest.getEmail());
                userService.deleteUser(user);
                return ResponseEntity.ok("User deleted successfully");
            }else {
                return ResponseEntity.badRequest().body("Cannot find user");
            }
        }else {
            return ResponseEntity.badRequest().body("Cannot delete account while courses are active");
        }
    }
    @PostMapping("/linktoChangePasswordStuff")
    public ResponseEntity<String> changePassword(@RequestBody UserRequest userRequest) {
        if (userService.doesUserExistByEmail(userRequest.getEmail())){
            User user = userService.getUserByEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            userService.changePassword(userRequest.getPassword(), userRequest.getNew_password());
        }
        return ResponseEntity.badRequest().body("Cannot find user");
    }
}
