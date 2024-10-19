package hugbo1.backend.Courses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table( name = "Courses" )
public class Course {

    @Id
    @Column( name = "course_id", unique = true)
    private String courseId;
    private String courseName;
    private String instructor;
    private String description;

    public String getCourseId() {
        courseId = courseName + 101;
        return courseId;
    }

    public void setCourseId(String course_id) {
        this.courseId = course_id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String course_name) {
        this.courseName = course_name;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
