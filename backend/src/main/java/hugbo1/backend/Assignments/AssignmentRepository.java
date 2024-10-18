package hugbo1.backend.Assignments;

import hugbo1.backend.Database.DatabaseConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentRepository {

    private DatabaseConnector databaseConnector;

    public AssignmentRepository() {
        this.databaseConnector = new DatabaseConnector();
        createAssignmentTable();
    }

    // Create the assignment table in the database
    public void createAssignmentTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Assignments ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "courseName TEXT NOT NULL, "
                + "name TEXT NOT NULL, "
                + "publishDate DATE NOT NULL, "
                + "dueDate DATE NOT NULL);";
        try (Connection conn = databaseConnector.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Add an assignment to the database
    public void addAssignment(Assignment assignment) {
        String sql = "INSERT INTO Assignments(courseName, name, publishDate, dueDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, assignment.getCourse().getName());
            pstmt.setString(2, assignment.getName());
            pstmt.setDate(3, new java.sql.Date(assignment.getPublishData().getTime()));
            pstmt.setDate(4, new java.sql.Date(assignment.getDueDate().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Fetch assignments (for future functionalities)
    public List<Assignment> getAssignmentsByCourse(String courseName) {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM Assignments WHERE courseName = ?";
        try (Connection conn = databaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                Date publishDate = rs.getDate("publishDate");
                Date dueDate = rs.getDate("dueDate");
                // Create Assignment object and add to list
                Assignment assignment = new Assignment(new Course(courseName), name, publishDate, dueDate);
                assignments.add(assignment);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return assignments;
    }
}
