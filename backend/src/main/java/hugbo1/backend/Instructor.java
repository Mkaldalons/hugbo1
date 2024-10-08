package hugbo1.backend;

import java.util.List;

public class Instructor extends User {
    private List<Course> courses;

    public Instructor(String userName, String name, String email, String password) {
        super(userName, name, email, password, true);
    }

    public List<Course> getCourses() {
        return courses;
    }
    public void addCourse(Course course) {
        courses.add(course);
    }
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public String toString(){
        return "Name: " + getName() + "Username: " + getUserName();
    }
}
