package hugbo1.backend;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/student")
    public String addStudentUser(@RequestBody User user) {
        Student student = new Student(user.getUserName(), user.getName(), user.getEmail(), user.getPassword());
        if (!userRepository.doesStudentUserExist(user.getUserName())) {
            userRepository.addStudent(student);
            return "Student registered successfully!";
        }
        return "Student already exists!";
    }

    // Endpoint to add an instructor
    @PostMapping("/instructor")
    public String addInstructorUser(@RequestBody User user) {
        Instructor instructor = new Instructor(user.getUserName(), user.getName(), user.getEmail(), user.getPassword());
        if (!userRepository.doesInstructorUserExist(user.getUserName())) {
            userRepository.addInstructor(instructor);
            return "Instructor registered successfully!";
        }
        return "Instructor already exists!";
    }
}
