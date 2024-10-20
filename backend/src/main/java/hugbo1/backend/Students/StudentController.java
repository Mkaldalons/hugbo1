package hugbo1.backend.Students;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

//    @PostMapping("/mapping-to-see-student-info")
//    public ResponseEntity<Student> studentInfo(@RequestBody Student student) {
//
//    }

    public static void main(String[] args){

    }
}
