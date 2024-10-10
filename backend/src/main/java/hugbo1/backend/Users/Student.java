package hugbo1.backend.Users;

import hugbo1.backend.Assignments.Course;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Course> enrolledCourses;

    // Constructor without 'id' parameter
    public Student(String userName, String name, String email, String password) {
        super(null, name, userName, email, password); // Passing 'null' for the ID initially
        this.enrolledCourses = new ArrayList<>();
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void addCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void removeCourse(Course course) {
        enrolledCourses.remove(course);
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Username: " + getUserName();
    }
}
