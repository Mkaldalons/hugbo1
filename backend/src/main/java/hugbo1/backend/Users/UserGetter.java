package hugbo1.backend.Users;

import hugbo1.backend.database.H2DatabaseConnector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserGetter {

    private final H2DatabaseConnector h2DatabaseConnector;

    public UserGetter() {
        this.h2DatabaseConnector = new H2DatabaseConnector();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        Connection connection = null;
        try {
            connection = h2DatabaseConnector.connect();
            List<User> users = h2DatabaseConnector.getAllUsers(connection);
            return ResponseEntity.ok(users);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
