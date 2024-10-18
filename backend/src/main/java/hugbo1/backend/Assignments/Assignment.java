package hugbo1.backend.Assignments;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name="Assignments")
public class Assignment {

    @Id
    @Column(name = "id")
    private int id;
    private String courseId;
    private String name;
    private Date dueDate;
    private String question_json;
    private String answer_json;

    public String getJson_data() {
        return json_data;
    }

    public void setJson_data(String json_data) {
        this.json_data = json_data;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String json_data;

    public Assignment() {
        super();
    }


    public String getCourseId() {
        return courseId;
    }
    public String getName () {
        return name;
    }
    public Date getDueDate () {
        return dueDate;
    }

    public void setName (String name){
        this.name = name;
    }

    public void setDueDate (Date dueDate){
        this.dueDate = dueDate;
    }

    public String toString () {
        return "";
    }
}

