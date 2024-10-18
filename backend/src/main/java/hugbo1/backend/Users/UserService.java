package hugbo1.backend.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    // Fetch a user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    // Check if a user exists by username
    public boolean doesUserExistByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    // Check if a user exists by email
    public boolean doesUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Check if a user exists by username and password (e.g., for login)
    public boolean authenticateUser(String username, String password) {
        return userRepository.existsByUserNameAndPassword(username, password);
    }
    public void addUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    public void changePassword(String oldPassword, String newPassword) {
        userRepository.changePassword(oldPassword, newPassword);
    }

}
