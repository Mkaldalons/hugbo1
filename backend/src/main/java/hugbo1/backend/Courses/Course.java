package hugbo1.backend.Courses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table( name = "Courses" )
public class Course {

    @Id
    @Column ( name = "course_id", unique = true )
    private String course_id;
    private String course_name;
    private String description;
    private String instructor;

    public Course(String course_name) {
        this.course_name = course_name;
    }

    public Course() {

    }

    public Course(String course_id, String course_name, String description, String instructor) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.description = description;
        this.instructor = instructor;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
