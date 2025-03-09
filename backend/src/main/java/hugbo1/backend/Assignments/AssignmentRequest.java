package hugbo1.backend.Assignments;

import java.time.LocalDate;
import java.util.List;

public class AssignmentRequest {
    private LocalDate dueDate;
    private List<QuestionRequest> questionRequest;
    private Integer courseId;
    private String assignmentName;
    private Boolean published;

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public List<QuestionRequest> getQuestionRequest() {
        return questionRequest;
    }

    public void setQuestionRequest(List<QuestionRequest> questionRequest) {
        this.questionRequest = questionRequest;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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
