package hugbo1.backend.Courses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/courses")
    public ResponseEntity<Map<String, Object>> createCourse(@RequestBody CourseRequest courseRequest) {
        Course course = new Course();
        course.setCourseName(courseRequest.getCourseName());
        course.setCourseId(course.getCourseId());
        course.setInstructor(courseRequest.getCreatedBy());
        if(courseService.doesCourseExist(course.getCourseId())) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Course already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }else {
            course.setDescription("missing");
            courseService.addCourse(course);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Course created");
            return ResponseEntity.ok(responseBody);
        }
    }
}
