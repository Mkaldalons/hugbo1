package hugbo1.backend.Users;

public class UserRequest {
    private String user_name;
    private String name;
    private String email;
    private String password;
    private boolean is_instructor;

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    private String new_password;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public boolean isIs_instructor() {
        return is_instructor;
    }

    public void setIs_instructor(boolean is_instructor) {
        this.is_instructor = is_instructor;
    }
}
