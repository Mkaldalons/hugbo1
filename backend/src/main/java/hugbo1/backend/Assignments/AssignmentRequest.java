package hugbo1.backend.Assignments;

import java.util.Date;
import java.util.List;

public class AssignmentRequest {
    private Date dueDate;
    private List<QuestionRequest> questionRequest;
    private String courseId;

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public List<QuestionRequest> getQuestionRequests() {
        return questionRequest;
    }

    public void setQuestionRequests(List<QuestionRequest> questionRequests) {
        this.questionRequest = questionRequests;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
