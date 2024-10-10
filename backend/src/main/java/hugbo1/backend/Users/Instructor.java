package hugbo1.backend.Users;

import hugbo1.backend.Assignments.Course;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {
    private List<Course> taughtCourses;

    // Constructor without 'id' parameter
    public Instructor(String userName, String name, String email, String password) {
        super(null, name, userName, email, password); // Passing 'null' for the ID initially
        this.taughtCourses = new ArrayList<>();
    }

    public List<Course> getTaughtCourses() {
        return taughtCourses;
    }

    public void addCourse(Course course) {
        taughtCourses.add(course);
    }

    public void removeCourse(Course course) {
        taughtCourses.remove(course);
    }

    @Override
    public String toString() {
        return "Instructor{name=" + getName() + ", username=" + getUserName() + "}";
    }
}
