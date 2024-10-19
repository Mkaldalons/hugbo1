package hugbo1.backend.Assignments;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class AssignmentRequest {
    private Date dueDate;
    private MultipartFile jsonData;
    private String courseId;

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public MultipartFile getJsonData() {
        return jsonData;
    }

    public void setJsonData(MultipartFile jsonData) {
        this.jsonData = jsonData;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
