package hugbo1.backend;

public class RegisterController {
    UserRepository userRepository = new UserRepository();
    UserController userController = new UserController();

    public RegisterController() {

    }

    public void addStudentUser(String username, String name, String email, String password) {
        Student student = new Student(username, name, email, password);
        if (!userController.doesStudentExist(username)) {
            userRepository.allStudents.add(student);
        }
    }
    public void addInstructorUser(String username, String name, String email, String password) {
        Instructor instructor = new Instructor(username, name, email, password);
        if (!userController.doesInstructorExist(username)) {
            userRepository.allInstructors.add(instructor);
        }
    }
}
