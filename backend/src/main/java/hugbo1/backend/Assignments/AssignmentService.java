package hugbo1.backend.Assignments;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class AssignmentService {

    @Autowired
    private final AssignmentRepository assignmentRepository;

    /** Use ObjectMapper from fasterxml.jackson.databind to convert the text
     * received from javascript to a json file.
     * @param textBody The text received from javascript of type Map<String, String>
     * @return ObjectMapper object containing the json file.
     */
    public String convertTextToJson(Map<String, String> textBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(textBody);
    }

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }
    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
    public Assignment getAssignmentById(int id) {
        return assignmentRepository.findById(id);
    }
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
    public void deleteAssignmentById(int id) {
        assignmentRepository.deleteById(id);
    }
    public void deleteAssignmentByName(String name) {
        assignmentRepository.deleteByName(name);
    }
    public void addAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }
}
