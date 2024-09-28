package hugbo1.backend;

import java.util.List;

public class Course {

    protected String name;
    protected String courseNumber;
    protected String description;
    protected Instructor instructor;
    private List<Student> enrolledStudents;

    public Course(String name, String courseNumber, String description, Instructor instructor) {
        this.name = name;
        this.courseNumber = courseNumber;
        this.description = description;
        this.instructor = instructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent(Student student) {
        this.enrolledStudents.add(student);
        student.addCourse(this);
    }
    public void unenrollStudent(Student student) {
        if (this.enrolledStudents.contains(student)) {
            this.enrolledStudents.remove(student);
            student.removeCourse(this);
        }
    }

}
