package hugbo1.backend.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public boolean doesUserExistByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    public boolean doesUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean authenticateUser(String username, String password) {
        return userRepository.existsByUserNameAndPassword(username, password);
    }
    public void addUser(User user) {
        userRepository.save(user);
    }
    public boolean isInstructor(String username) {
        return userRepository.findIsInstructorByUsername(username);
    }
    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    public void changeEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        userRepository.save(user);
    }
    public void updateRecoveryEmail(String username, String recoveryEmail) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
    
        user.setRecoveryEmail(recoveryEmail);
        userRepository.save(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void updateUser(User user) {
        userRepository.save(user);
    }

}
