package hugbo1.backend.Courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hugbo1.backend.Students.Student;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table( name = "Courses" )
public class Course {

    @Id
    @Column( name = "course_id", unique = true)
    private String courseId;
    private String courseName;
    private String instructor;
    private String description;


    @ManyToMany( mappedBy = "courses")
    @JsonIgnore
    private Set<Student> students;

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

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
