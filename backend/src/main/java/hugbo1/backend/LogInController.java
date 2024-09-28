package hugbo1.backend;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class LogInController {

    private final UserRepository userRepository;

    private final UserController userController;

    public LogInController(UserRepository userRepository, UserController userController) {
        this.userRepository = userRepository;
        this.userController = userController;
    }

    // Login Endpoint for Student
    @PostMapping("/student/login")
    public boolean loginStudent(@RequestParam String userName, @RequestParam String password) {
        return studentUserExists(userName) && isStudentPasswordValid(password);
    }

    // Login Endpoint for Instructor
    @PostMapping("/instructor/login")
    public boolean loginInstructor(@RequestParam String userName, @RequestParam String password) {
        return instructorUserExists(userName) && isInstructorPasswordValid(password);
    }

    public boolean studentUserExists(String userName) {
        return userController.doesStudentExist(userName);
    }

    public boolean instructorUserExists(String userName) {
        return userController.doesInstructorExist(userName);
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
