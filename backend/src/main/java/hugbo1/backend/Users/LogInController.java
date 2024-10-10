package hugbo1.backend.Users;

import hugbo1.backend.database.H2DatabaseConnector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LogInController {

    private final H2DatabaseConnector h2DatabaseConnector;

    public LogInController() {
        this.h2DatabaseConnector = new H2DatabaseConnector();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Connection connection = null;
        try {
            // Connect to the database
            connection = h2DatabaseConnector.connect();
            // Retrieve all users from the database
            List<User> users = h2DatabaseConnector.getAllUsers(connection);

            // Find the user with the matching username
            User matchedUser = users.stream()
                    .filter(user -> user.getUserName().equals(loginRequest.getUserName()))
                    .findFirst()
                    .orElse(null);

            if (matchedUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            // Check if the password matches
            if (!matchedUser.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }

            // Return success message if authentication succeeds
            return ResponseEntity.ok("Login successful");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + e.getMessage());
        } finally {
            try {
                // Close the connection if it was opened
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
