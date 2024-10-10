package hugbo1.backend.Assignments;

import java.util.Date;

public class Assignment {
    private Course course;
    private String name;
    private Date publishData;
    private Date dueDate;

    public Assignment(Course course, String name, Date publishData, Date dueDate) {
        this.course = course;
        this.name = name;
        this.publishData = publishData;
        this.dueDate = dueDate;
    }

    public Course getCourse() { return course; }
    public String getName() { return name; }
    public Date getPublishData() { return publishData; }
    public Date getDueDate() { return dueDate; }

    public void setCourse(Course course) { this.course = course; }
    public void setName(String name) { this.name = name; }
    public void setPublishData(Date publishData) { this.publishData = publishData; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public String toString() {
        return "Assignment{name='" + name + "', course='" + course.getName() + "', publishDate=" + publishData + ", dueDate=" + dueDate + "}";
    }
}
