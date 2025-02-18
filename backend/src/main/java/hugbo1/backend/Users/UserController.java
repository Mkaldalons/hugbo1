
package hugbo1.backend.Users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @Value("${profile.image.upload.dir}")
    private String uploadDir;

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
    public ResponseEntity<Map<String, Object>> deleteAccount(@RequestBody DeleteAccountRequest deleteAccountRequest) {
        Map<String, Object> responseBody = new HashMap<>();
        User user = userService.getUserByUserName(deleteAccountRequest.getUsername());
        if (userService.doesUserExistByEmail(user.getEmail())) {
            userService.deleteUser(user);
            responseBody.put("status", "Account deleted successfully");
            return ResponseEntity.ok(responseBody);
        }else {
            responseBody.put("status", "Wrong email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
    }
    @PostMapping("/update-recovery-email")
    public ResponseEntity<Map<String, Object>> updateRecoveryEmail(@RequestBody UserRequest userRequest) {
        Map<String, Object> responseBody = new HashMap<>();

        try {
            userService.updateRecoveryEmail(userRequest.getUsername(), userRequest.getRecoveryEmail());
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

    @PostMapping("/upload-profile-image")
    public ResponseEntity<Map<String, Object>> uploadProfileImage(
        @RequestParam("profileImage") MultipartFile file,
        @RequestParam("username") String username) {

        Map<String, Object> responseBody = new HashMap<>();

        User user = userService.getUserByUserName(username);
        if (user == null) {
            responseBody.put("status", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        if (file.isEmpty()) {
            responseBody.put("status", "File is empty. Please select an image.");
            return ResponseEntity.badRequest().body(responseBody);
        }

        try {
            // Step 1: Delete the existing profile image if it exists
            String oldProfileImagePath = user.getProfileImagePath();
            if (oldProfileImagePath != null && !oldProfileImagePath.isEmpty()) {
                String oldFileName = Paths.get(oldProfileImagePath).getFileName().toString();
                Path oldFilePath = Paths.get(uploadDir, oldFileName);

                System.out.println("Attempting to delete old profile image at path: " + oldFilePath);
                File oldFile = oldFilePath.toFile();
                if (oldFile.exists()) {
                    boolean deleted = oldFile.delete(); 
                    System.out.println("Old profile image deleted: " + oldProfileImagePath + " - Success: " + deleted);
                } else {
                    System.out.println("Old profile image not found at path: " + oldFilePath);
                }
            }

            // Step 2: Save the new profile image
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path newFilePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(newFilePath.getParent());
            Files.copy(file.getInputStream(), newFilePath);

            // Step 3: Update the user's profile image path in the database
            user.setProfileImagePath("uploads/profile-images/" + fileName);
            userService.addUser(user);

            responseBody.put("status", "Profile image uploaded successfully");
            responseBody.put("imagePath", "uploads/profile-images/" + fileName);
            return ResponseEntity.ok(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            responseBody.put("status", "Error uploading profile image");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @GetMapping("/get-profile-image")
    public ResponseEntity<Map<String, Object>> getProfileImage(@RequestParam("username") String username) {
        Map<String, Object> responseBody = new HashMap<>();
        
        User user = userService.getUserByUserName(username);
        if (user == null) {
            responseBody.put("status", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        String profileImagePath = user.getProfileImagePath();
        if (profileImagePath == null || profileImagePath.isEmpty()) {
            responseBody.put("status", "No profile image found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        responseBody.put("status", "Profile image found");
        responseBody.put("imagePath", profileImagePath); // Return the path
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/users/{userName}")
    public ResponseEntity<User> getUserInfo(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

