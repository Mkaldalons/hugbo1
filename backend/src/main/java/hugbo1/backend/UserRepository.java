package hugbo1.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository {
    MockDatabase database;
    List<Student> allStudents;
    List<Instructor> allInstructors;
    List<User> allUsers;

    public UserRepository() {
        this.database = new MockDatabase();
        this.allStudents = database.getAllStudents();
        this.allInstructors = database.getAllInstructors();
        this.allUsers = new ArrayList<>();
        allUsers.addAll(allStudents);
        allUsers.addAll(allInstructors);
    }
    public Student getStudentByUsername(String username) {
        for (Student student : allStudents) {
            if (Objects.equals(username, student.getUserName())){
                return student;
            }
        }
        return null;
    }

    public Instructor getInstructorByUsername(String username) {
        for (Instructor instructor : allInstructors) {
            if (Objects.equals(username, instructor.getUserName())){
                return instructor;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (Objects.equals(username, user.getUserName())){
                return user;
            }
        }return null;
    }
    public boolean doesUserExist(String username) {
        for (User user : allUsers) {
            if (Objects.equals(username, user.getUserName())){
                return true;
            }
        }return false;
    }
    public boolean doesStudentUserExist(String userName) {
        return allStudents.contains(getStudentByUsername(userName));
    }
    public boolean doesInstructorUserExist(String userName) {
        return allInstructors.contains(getInstructorByUsername(userName));
    }
    public void addStudent(Student student) {
        allStudents.add(student);
    }
    public void addInstructor(Instructor instructor) {
        allInstructors.add(instructor);
    }
    public void addUser(User user) {
        allUsers.add(user);
    }
    public static void main(String[] args){
        UserRepository userRepository = new UserRepository();
        User testUser = new User("name", "Name", "name@email.com", "password");
        userRepository.addUser(testUser);
        System.out.println(userRepository.getUserByUsername("name"));
        if (userRepository.doesUserExist("name")){
            System.out.println("This user exists");
        }
    }
}
