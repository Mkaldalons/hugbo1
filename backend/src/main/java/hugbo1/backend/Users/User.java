package hugbo1.backend.Users;

public class User {
    private Integer id;
    private String name;
    private String userName;
    private String email;
    private String password;

    public User(Integer id, String name, String userName, String email, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setId(Integer id) { this.id = id; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', userName='" + userName + "', email='" + email + "'}";
    }
}
