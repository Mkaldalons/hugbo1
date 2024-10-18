package hugbo1.backend.Users;

import hugbo1.backend.Courses.Course;

import java.util.List;

public class Instructor extends User {
    private List<Course> courses;

    public Instructor(String userName, String name, String email, String password) {
        super(userName, name, email, password, true);
    }

    public List<Course> getCourses() {
        return courses;
    }
    public void addCourse(Course course) {
        courses.add(course);
    }
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    //public void createAssignment(Course course, String name, Date publishDate, Date dueDate) {
    //    Assignment assignment = new Assignment(course, name, publishDate, dueDate);
    //    AssignmentRepository repository = new AssignmentRepository();
    //    repository.addAssignment(assignment);
    //}

    public String toString(){
        return "Name: " + getName() + "Username: " + getUserName();
    }
}