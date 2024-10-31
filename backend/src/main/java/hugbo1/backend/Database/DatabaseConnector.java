package hugbo1.backend.Database;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import hugbo1.backend.Users.User;

public class DatabaseConnector {
    private static final String url = "jdbc:sqlite:src/main/resources/learningSquare.db";

    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established: " + connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Users ("
                + " userName TEXT NOT NULL, "
                + " name TEXT NOT NULL, "
                + " email TEXT UNIQUE NOT NULL, "
                + " password TEXT NOT NULL, "
                + " instructor boolean NOT NULL"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);
            System.out.println("Table created successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void addUser(User user) {
        String addUserSQL = "INSERT INTO Users (userName, name, email, password, instructor) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(addUserSQL)) {

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.getisInstructor());
            preparedStatement.executeUpdate(); // Execute the query
            System.out.println("User added successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //DatabaseConnector connector = new DatabaseConnector();
        //connector.createTable();
        //connector.addUser(new User("userName3", "Name3", "email3@test.is", "password3", false));
    }
}
