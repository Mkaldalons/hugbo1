package hugbo1.backend.Assignments;

import java.util.Date;
import java.util.List;

public class AssignmentRequest {
    private Date dueDate;
    private List<QuestionRequest> questionRequest;
    private String courseId;

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public List<QuestionRequest> getQuestionRequest() {
        return questionRequest;
    }

    public void setQuestionRequest(List<QuestionRequest> questionRequest) {
        this.questionRequest = questionRequest;
    }

    private int assignmentId;

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
