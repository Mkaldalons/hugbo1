package hugbo1.backend.Students;

import hugbo1.backend.Courses.Course;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }
    public Student getStudentByName(String name) {
        return studentRepository.findByUserName(name);
    }
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

}
