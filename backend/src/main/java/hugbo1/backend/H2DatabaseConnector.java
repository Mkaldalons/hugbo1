package hugbo1.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// Þetta virkar ekki
public class H2DatabaseConnector {
    private static final String JDBC_URL = "jdbc:h2:file:./src/main/resources/testDatabase.mv.db";

    public Connection connect() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, "sa", "");
            System.out.println("Connection to H2 database established!");
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database: " + e.getMessage());
        }
        return connection;
    }

    public List<User> getAllUsers(Connection connection) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT name, userName, email, password FROM PUBLIC.USERS";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String userName = rs.getString("userName");
                String email = rs.getString("email");
                String password = rs.getString("password");

                User user = new User(name, userName, email, password);
                userList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching users: " + e.getMessage());
        }
        return userList;
    }
    public void addUser(Connection connection, User user) {
        String insertSQL = "INSERT INTO PUBLIC.USERS (name, userName, email, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User inserted successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error while inserting user: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        H2DatabaseConnector h2Database = new H2DatabaseConnector();
        Connection connection = h2Database.connect();
        User testUser = new User("Test Name", "testName", "test@test.com", "testPassword");
        h2Database.addUser(connection, testUser);
        System.out.println(h2Database.getAllUsers(connection));
    }
}