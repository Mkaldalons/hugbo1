package hugbo1.backend.Assignments;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table( name="Assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Use IDENTITY for auto-increment
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    private Date dueDate;
    private String jsonData;
    private String courseId;
    private String assignmentName;


    public int getId() {
        return id+1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date date) {
        this.dueDate = date;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }
}
