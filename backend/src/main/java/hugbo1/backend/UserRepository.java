package hugbo1.backend;

import java.util.List;

public class UserRepository {
    MockDatabase database;
    List<Student> allStudents;
    List<Instructor> allInstructors;

    public UserRepository() {
        this.database = new MockDatabase();
        this.allStudents = database.getAllStudents();
        this.allInstructors = database.getAllInstructors();
    }
}
