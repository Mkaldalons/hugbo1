package hugbo1.backend.Assignments;

import java.time.LocalDate;
import java.util.List;

public class AssignmentRequest {
    private LocalDate dueDate;
    private List<QuestionRequest> questionRequest;
    private Integer courseId;
    private String assignmentName;

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<QuestionRequest> getQuestionRequests() {
        return questionRequest;
    }

    public void setQuestionRequests(List<QuestionRequest> questionRequests) {
        this.questionRequest = questionRequests;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
