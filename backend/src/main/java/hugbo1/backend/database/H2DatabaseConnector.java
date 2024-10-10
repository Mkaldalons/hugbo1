package hugbo1.backend.database;

import hugbo1.backend.Assignments.Course;
import hugbo1.backend.Users.User;
import hugbo1.backend.Assignments.Assignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2DatabaseConnector {
    private static final String JDBC_URL = "jdbc:h2:file:./src/main/resources/testDatabase.mv.db";

    // Connect to the database
    public Connection connect() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, "sa", "");
            System.out.println("Connection to H2 database established!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database: " + e.getMessage());
            throw e;
        }
    }

    public void createTables(Connection connection) {
        String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS PUBLIC.USERS (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "userName VARCHAR(255) NOT NULL UNIQUE, " +
                "email VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL" +
                ")";

        String createAssignmentsTableSQL = "CREATE TABLE IF NOT EXISTS PUBLIC.ASSIGNMENTS (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "course VARCHAR(255) NOT NULL, " +
                "name VARCHAR(255) NOT NULL, " +
                "publishDate DATE NOT NULL, " +
                "dueDate DATE NOT NULL" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsersTableSQL);
            System.out.println("USERS table created or already exists.");

            stmt.execute(createAssignmentsTableSQL);
            System.out.println("ASSIGNMENTS table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error while creating tables: " + e.getMessage());
        }

        String resetSequenceSQL = "ALTER TABLE PUBLIC.USERS ALTER COLUMN id RESTART WITH ";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(resetSequenceSQL);
            System.out.println("User ID sequence reset to ");
        } catch (SQLException e) {
            System.out.println("Error while resetting user ID sequence: " + e.getMessage());
        }
    }

    public List<User> getAllUsers(Connection connection) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT id, name, userName, email, password FROM PUBLIC.USERS";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String userName = rs.getString("userName");
                String email = rs.getString("email");
                String password = rs.getString("password");

                User user = new User(id, name, userName, email, password);
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


    public void addAssignment(Connection connection, Assignment assignment) {
        String insertSQL = "INSERT INTO PUBLIC.ASSIGNMENTS (course, name, publishDate, dueDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, assignment.getCourse().getName());
            pstmt.setString(2, assignment.getName());
            pstmt.setDate(3, new java.sql.Date(assignment.getPublishData().getTime()));
            pstmt.setDate(4, new java.sql.Date(assignment.getDueDate().getTime()));

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Assignment inserted successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error while inserting assignment: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        H2DatabaseConnector h2Database = new H2DatabaseConnector();
        Connection connection = h2Database.connect();

        h2Database.createTables(connection);


        User testUser = new User(null, "Test Name1", "testName1", "test1@test.com", "testPassword1");
        h2Database.addUser(connection, testUser);
        User testUser1 = new User(null, "Test Name2", "testName2", "test2@test.com", "testPassword2");
        h2Database.addUser(connection, testUser1);
        User testUser2 = new User(null, "Test Name3", "testName3", "test3@test.com", "testPassword3");
        h2Database.addUser(connection, testUser2);
        User testUser3 = new User(null, "Test Name4", "testName4", "test4@test.com", "testPassword4");
        h2Database.addUser(connection, testUser3);


        List<User> users = h2Database.getAllUsers(connection);
        users.forEach(System.out::println);


        Course testCourse = new Course("Test Course");
        Assignment testAssignment = new Assignment(testCourse, "Test Assignment", new java.util.Date(), new java.util.Date());
        h2Database.addAssignment(connection, testAssignment);


        connection.close();
    }
}
