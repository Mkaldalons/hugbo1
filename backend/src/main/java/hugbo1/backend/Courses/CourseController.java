package hugbo1.backend.Courses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/linkToCourseCreation")
    public ResponseEntity<String> createCourse(@RequestBody CourseRequest courseRequest) {
        if(!courseService.doesCourseExist(courseRequest.getCourseId())) {
            Course course = new Course(
                    courseRequest.getCourseName(),
                    courseRequest.getCourseId(),
                    courseRequest.getDescription(),
                    courseRequest.getInstructor()
            );
            courseService.addCourse(course);
            return ResponseEntity.ok("Course created");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course already exists");
        }
    }

}
