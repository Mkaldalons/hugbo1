
package hugbo1.backend.Students;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hugbo1.backend.Assignments.AssignmentSubmission;
import hugbo1.backend.Courses.Course;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="Students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private String userName;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "Student_Courses",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn(name="course_id")
    )
    @JsonIgnore
    private Set<Course> courses;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private Set<AssignmentSubmission> submissions;

    public Student() {

    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
