package hugbo1.backend;

import java.util.Date;

public class Assignment {
    protected Course course;
    protected String name;
    protected Date publishData;
    protected Date dueDate;

    public Assignment(Course course, String name, Date publishData, Date dueDate) {
        this.course = course;
        this.name = name;
        this.publishData = publishData;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublishData() {
        return publishData;
    }

    public void setPublishData(Date publishData) {
        this.publishData = publishData;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
