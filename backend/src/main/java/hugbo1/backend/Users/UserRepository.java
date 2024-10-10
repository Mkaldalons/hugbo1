package hugbo1.backend.Users;

import hugbo1.backend.database.DatabaseConnector;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository {
    List<Student> students;
    List<Instructor> instructors;
    List<User> users;
    DatabaseConnector databaseConnector;

    public UserRepository() {
        this.databaseConnector = new DatabaseConnector();
        databaseConnector.createTable();
        this.students = getStudents();
        this.instructors = getInstructors();
        this.users = getUsers();
    }

    public List<Student> getStudents() {
        String sql = "SELECT * FROM Users WHERE instructor = false";

        List<Student> students = new ArrayList<>();

        try (Connection connection = databaseConnector.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String userName = resultSet.getString("userName");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                Student student = new Student(userName, name, email, password);
                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return students;
    }

    public List<Instructor> getInstructors() {
        String sql = "SELECT * FROM Users WHERE instructor = true";

        List<Instructor> instructors = new ArrayList<>();

        try (Connection connection = databaseConnector.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String userName = resultSet.getString("userName");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                Instructor instructor = new Instructor(userName, name, email, password);
                instructors.add(instructor);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return instructors;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.addAll(students);
        users.addAll(instructors);
        return users;
    }


    public Student getStudentByUsername(String username) {
        for (Student student : students) {
            if (Objects.equals(username, student.getUserName())){
                return student;
            }
        }
        return null;
    }

    public Instructor getInstructorByUsername(String username) {
        for (Instructor instructor : instructors) {
            if (Objects.equals(username, instructor.getUserName())){
                return instructor;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (Objects.equals(username, user.getUserName())){
                return user;
            }
        }return null;
    }
    public boolean doesUserExist(String username) {
        for (User user : users) {
            if (Objects.equals(username, user.getUserName())){
                System.out.println("This user does exist");
                return true;
            }
            System.out.println("This user does not exist");
        }return false;
    }
    public boolean doesStudentUserExist(String userName) {
        return students.contains(getStudentByUsername(userName));
    }
    public boolean doesInstructorUserExist(String userName) {
        return instructors.contains(getInstructorByUsername(userName));
    }
    public void addStudent(Student student) {
        students.add(student);
    }
    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    public void addUser(User user) {
        databaseConnector.addUser(user);
        users.add(user);
    }
    public static void main(String[] args){
        UserRepository userRepository = new UserRepository();
        System.out.println(userRepository.users);
    }
}