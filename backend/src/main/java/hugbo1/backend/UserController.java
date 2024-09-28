package hugbo1.backend;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {
    UserRepository userRepository = new UserRepository();

    public UserController() {
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

}
