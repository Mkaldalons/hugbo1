package hugbo1.backend.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Find
    User findByUserName(String username);
    User findByEmail(String email);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    boolean existsByUserNameAndPassword(String username, String password);
    @Query("SELECT u.isInstructor FROM User u WHERE u.userName = :username")
    Boolean findIsInstructorByUsername(@Param("username") String username);




    void deleteByUserName(String username);

}