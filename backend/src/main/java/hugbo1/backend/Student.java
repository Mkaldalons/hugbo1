package hugbo1.backend;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Course> enrolledCourses;

    public Student(String userName, String name, String email, String password) {
        super(userName, name, email, password);
        this.enrolledCourses = new ArrayList<>();
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

}