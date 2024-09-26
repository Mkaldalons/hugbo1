package hugbo1.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserController {
    UserRepository userRepository = new UserRepository();

    public UserController() {
    }
    public Student getStudentByUsername(String username) {
        for (Student student : userRepository.allStudents) {
            if (Objects.equals(username, student.getUserName())){
                return student;
            }
        }
        return null;
    }

    public Instructor getInstructorByUsername(String username) {
        for (Instructor instructor : userRepository.allInstructors) {
            if (Objects.equals(username, instructor.getUserName())){
                return instructor;
            }
        }
        return null;
    }

    public boolean doesStudentExist(String username) {
        for (Student student : userRepository.allStudents) {
            if (Objects.equals(username, student.getUserName())){
                return true;
            }
        }
        return false;
    }

    public boolean doesInstructorExist(String username) {
        for (Instructor instructor : userRepository.allInstructors) {
            if (Objects.equals(username, instructor.getUserName())){
                return true;
            }
        }
        return false;
    }

    public boolean isInstructorPasswordValid(String password) {
        for (Instructor instructor : userRepository.allInstructors) {
            if (Objects.equals(password, instructor.getPassword())){
                return true;
            }
        }
        return false;
    }
    public boolean isStudentPasswordValid(String password) {
        for (Student student : userRepository.allStudents) {
            if (Objects.equals(password, student.getPassword())){
                return true;
            }
        }
        return false;
    }
}
