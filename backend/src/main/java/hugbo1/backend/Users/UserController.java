
package hugbo1.backend.Users;

import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class UserController {
    UserRepository userRepository = new UserRepository();

    public UserController() {
    }


    public boolean doesStudentExist(String username) {
        for (Student student : userRepository.students) {
            if (Objects.equals(username, student.getUserName())){
                return true;
            }
        }
        return false;
    }

    public boolean doesInstructorExist(String username) {
        for (Instructor instructor : userRepository.instructors) {
            if (Objects.equals(username, instructor.getUserName())){
                return true;
            }
        }
        return false;
    }

}
