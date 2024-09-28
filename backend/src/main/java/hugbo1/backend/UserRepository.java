package hugbo1.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository {
    MockDatabase database;
    List<Student> allStudents;
    List<Instructor> allInstructors;

    public UserRepository() {
        this.database = new MockDatabase();
        this.allStudents = database.getAllStudents();
        this.allInstructors = database.getAllInstructors();
    }
    public Student getStudentByUsername(String username) {
        for (Student student : allStudents) {
            if (Objects.equals(username, student.getUserName())){
                return student;
            }
        }
        return null;
    }

    public Instructor getInstructorByUsername(String username) {
        for (Instructor instructor : allInstructors) {
            if (Objects.equals(username, instructor.getUserName())){
                return instructor;
            }
        }
        return null;
    }
    public boolean doesStudentUserExist(String userName) {
        return allStudents.contains(getStudentByUsername(userName));
    }
    public boolean doesInstructorUserExist(String userName) {
        return allInstructors.contains(getInstructorByUsername(userName));
    }
    public void addStudent(Student student) {
        allStudents.add(student);
    }
    public void addInstructor(Instructor instructor) {
        allInstructors.add(instructor);
    }
}
