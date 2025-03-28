package hugbo1.backend.Users;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    private String userName;
    private String name;
    @Id
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    private String password;
    private boolean isInstructor;

    private byte[] profileImageData;
    private String recoveryEmail;

    public User() {

    }
    public User(String userName, String name, String email, String password, boolean isInstructor) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isInstructor = isInstructor;
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isInstructor() {
        return isInstructor;
    }

    public void setIsInstructor(boolean isInstructor) {
        this.isInstructor = isInstructor;
    }

    public String toString(){
        return "Name: " + name + "\n User name: " + userName;
    }
    
    public byte[] getProfileImageData() {
        return profileImageData;
    }

    public void setProfileImageData(byte[] profileImagePath) {
        this.profileImageData = profileImagePath;
    }

    public String getRecoveryEmail() {
        return recoveryEmail;
    }

    public void setRecoveryEmail(String recoveryEmail) {
        this.recoveryEmail = recoveryEmail;
    }
}