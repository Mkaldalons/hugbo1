
package hugbo1.backend.Users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Value("${profile.image.upload.dir}")
    private String uploadDir;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<Map<String, Object>> updatePassword(String newPassword, String oldPassword, String userName) {
        Map<String, Object> responseBody = new HashMap<>();

        User user = userService.getUserByUserName(userName);

        if(oldPassword.equals(user.getPassword())){
            user.setPassword(newPassword);
            userService.changePassword(user, user.getPassword());
            responseBody.put("status", "Password changed successfully");
            return ResponseEntity.ok(responseBody);
        } else {
            responseBody.put("status", "Wrong password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
    }
    @DeleteMapping("{userName}")
    public ResponseEntity<Map<String, Object>> deleteAccount(@PathVariable String userName) {
        Map<String, Object> responseBody = new HashMap<>();
        User user = userService.getUserByUserName(userName);
        if (userService.doesUserExistByEmail(user.getEmail())) {
            userService.deleteUser(user);
            responseBody.put("status", "Account deleted successfully");
            return ResponseEntity.ok(responseBody);
        }else {
            responseBody.put("status", "Wrong email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
    }

    public ResponseEntity<Map<String, Object>> updateRecoveryEmail(String recoveryEmail, String userName) {
        Map<String, Object> responseBody = new HashMap<>();

        try {
            userService.updateRecoveryEmail(userName, recoveryEmail);
            responseBody.put("status", "Recovery email updated successfully");
            return ResponseEntity.ok(responseBody);
        } catch (IllegalArgumentException e) {
            responseBody.put("status", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            responseBody.put("status", "Error updating recovery email");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PatchMapping("{userName}")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable String userName) {
        System.out.println("Update user request called");
        if (userUpdateRequest.getProfileImageData() != null || userUpdateRequest.getProfileImageData().length != 0) {
            System.out.println("Update profile image data called");
            return uploadProfileImage(userUpdateRequest.getProfileImageData(), userName);
        }
        if (  userUpdateRequest.getNewPassword() != null && userUpdateRequest.getOldPassword() != null ) {
            if(!userUpdateRequest.getNewPassword().isEmpty() && !userUpdateRequest.getOldPassword().isEmpty()) {
                return updatePassword(userUpdateRequest.getNewPassword(), userUpdateRequest.getOldPassword(), userName);
            }
        }
        if( userUpdateRequest.getRecoveryEmail() != null ) {
            if(!userUpdateRequest.getRecoveryEmail().isEmpty())
            {
                return updateRecoveryEmail(userUpdateRequest.getRecoveryEmail(), userName);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Map<String, Object>> uploadProfileImage(byte[] file, String userName) {

        Map<String, Object> responseBody = new HashMap<>();
        User user = userService.getUserByUserName(userName);
        if (user == null) {
            responseBody.put("status", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
        System.out.println("User is not null");
        if (file.length == 0) {
            System.out.println("File is empty");
            responseBody.put("status", "File is empty. Please select an image.");
            return ResponseEntity.badRequest().body(responseBody);
        }
        System.out.println("File is not empty");
        try {
            user.setProfileImageData(file);
            userService.updateUser(user);
            System.out.println("Profile image updated successfully");
            responseBody.put("status", user.getProfileImageData());
            return ResponseEntity.ok(responseBody);
        }catch (Exception e) {
            System.out.println("Caught Exception");
            responseBody.put("status", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("{userName}/profileImage")
    public ResponseEntity<Map<String, Object>> getProfileImage(@PathVariable String userName) {
        Map<String, Object> responseBody = new HashMap<>();
        
        User user = userService.getUserByUserName(userName);
        if (user == null) {
            responseBody.put("status", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        byte[] profileImagePath = user.getProfileImageData();
        if (profileImagePath == null || profileImagePath.length == 0) {
            responseBody.put("status", "No profile image found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        responseBody.put("status", "Profile image found");
        responseBody.put("imagePath", profileImagePath);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("{userName}")
    public ResponseEntity<User> getUserInfo(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}

