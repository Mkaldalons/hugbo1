package hugbo1.backend.Users;

import hugbo1.backend.database.MockDatabase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    public final MockDatabase database;
    public final List<Student> allStudents;
    public final List<Instructor> allInstructors;
    public final List<User> allUsers;
    public final AtomicInteger idGenerator = new AtomicInteger(1); // ID generator

    public UserRepository() {
        this.database = new MockDatabase();
        this.allStudents = database.getAllStudents();
        this.allInstructors = database.getAllInstructors();
        this.allUsers = new ArrayList<>();
        allUsers.addAll(allStudents);
        allUsers.addAll(allInstructors);
    }

    public boolean doesUserExist(String username) {
        return allUsers.stream().anyMatch(user -> Objects.equals(username, user.getUserName()));
    }

    public void addUser(User user) {
        // Generate and set an ID if not already set
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        allUsers.add(user);
    }

    public User getUserByUsername(String username) {
        return allUsers.stream()
                .filter(user -> Objects.equals(username, user.getUserName()))
                .findFirst()
                .orElse(null);
    }


    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        User testUser = new User(null, "name", "Name", "name@email.com", "password");
        userRepository.addUser(testUser);
        System.out.println(userRepository.getUserByUsername("Name"));
        if (userRepository.doesUserExist("Name")) {
            System.out.println("This user exists");
        }
    }
}
