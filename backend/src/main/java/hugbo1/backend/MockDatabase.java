package hugbo1.backend;

import java.util.ArrayList;
import java.util.List;

public class MockDatabase {
    public List<Student> students;
    public List<Instructor> instructors;

    public MockDatabase() {
        students = new ArrayList<>();
        instructors = new ArrayList<>();

        students.add(new Student("nos2", "Name of Student", "nos2@hi.is", "password1"));
        students.add(new Student("nos1", "Name of Student", "nos1@hi.is", "password2"));
        students.add(new Student("nos3", "Name of Student", "nos3@hi.is", "password3"));

        instructors.add(new Instructor("noi1", "Name of Instructor", "noi1@hi.is", "password11"));
    }
    public List<Student> getAllStudents() {
        return students;
    }
    public List<Instructor> getAllInstructors() {
        return instructors;
    }
}
