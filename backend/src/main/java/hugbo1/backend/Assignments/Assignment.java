package hugbo1.backend.Assignments;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table( name="Assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Use IDENTITY for auto-increment
    @Column(name = "assignment_id", unique = true, nullable = false)
    private int assignmentId;
    private LocalDateTime dueDate;
    private String jsonData;
    private Integer courseId;
    private String assignmentName;
    private Boolean published = Boolean.FALSE;

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime date) {
        this.dueDate = date;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public boolean isPublished() {
        return published != null ? published : false;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
