package hugbo1.backend.Assignments;

import java.util.List;

public class QuestionRequest {
    private String question;
    private List<String> options;  // options is a List of strings, not a String
    private String correctAnswer;

    // Getters and setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;  // This is a list, not a string
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
