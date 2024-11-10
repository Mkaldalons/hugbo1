import { useEffect, useState } from "react";
import "./CourseDetailsView.css";
import axios from "axios";
import { useParams } from "react-router-dom";

const CourseDetailsView = () => {
  const [studentsLoading, setStudentsLoading] = useState(false);
  const [studentsError, setStudentsError] = useState(null);

  const [students, setStudents] = useState([
    { id: 1, name: "John Doe", username: "johndoe", grade: 90 },
    { id: 2, name: "Jane Smith", username: "janesmith", grade: 85 },
    { id: 3, name: "Jim Beam", username: "jimbeam", grade: 70 },
  ]);

  const [assignmentsLoading, setAssignmentsLoading] = useState(false);
  const [assignmentsError, setAssignmentsError] = useState(null);

  const [assignments, setAssignments] = useState([
    { id: 1, title: "Assignment 1", averageGrade: 90 },
    { id: 2, title: "Assignment 2", averageGrade: 85 },
    { id: 3, title: "Assignment 3", averageGrade: 70 },
  ]);

  const [studentToAdd, setStudentToAdd] = useState("");

  const { courseId } = useParams();

  useEffect(() => {
    // fetchStudents();
    // fetchAssignments();
  }, []);

  const fetchStudents = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/students?courseId=${courseId}`);
      setStudents(response.data);
      setStudentsError("");
    } catch (error) {
      setStudentsError(error.message);
      console.error("Error fetching students:", error);
    } finally {
      setStudentsLoading(false);
    }
  };
  
  const fetchAssignments = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/assignments?courseId=${courseId}`);
      setAssignments(response.data);
      setAssignmentsError("");
    } catch (error) {
      setAssignmentsError(error.message);
      console.error("Error fetching assignments:", error);
    } finally {
      setAssignmentsLoading(false);
    }
  };
  

  const handleAddStudentToCourse = async () => {
    const response = await axios
      .post("http://localhost:8080/students/add", {
        userName: studentToAdd,
        courseId: courseId,
      })
      .catch((error) => {
        console.error("Error adding student to course:", error);
      });

    if (response.status === 200) {
      alert("Student added to course successfully");
      setStudentToAdd("");
      fetchStudents();
    } else {
      alert("Failed to add student to course");
    }

    console.log(response.data);
  };

  const handleDeleteStudent = (studentId) => {
    if (window.confirm("Ertu viss um að þú viljir eyða notanda?")) {
      const filteredStudents = students.filter(
        (student) => student.id !== studentId
      );
      setStudents(filteredStudents);
      // TODO: Kalla í bakenda
    }
  };

  return (
    <div className="course-details-container">
      <h1 className="course-title">Course Details</h1>
      <div className="add-student-form">
        <form
          onSubmit={(e) => {
            e.preventDefault();
            handleAddStudentToCourse();
          }}
        >
          <input
            type="text"
            placeholder="Enter username of student to add"
            value={studentToAdd}
            onChange={(e) => setStudentToAdd(e.target.value)}
          />
          <button type="submit" className="base-button add-button">
            Add student to course
          </button>
        </form>
      </div>
      <div className="course-content">
        <StudentList
          students={students}
          isLoading={studentsLoading}
          error={studentsError}
          onDeleteStudent={handleDeleteStudent}
        />
        <AssignmentList
          assignments={assignments}
          isLoading={assignmentsLoading}
          error={assignmentsError}
        />
      </div>
    </div>
  );
};

const StudentList = ({ students, isLoading, error, onDeleteStudent }) => {
  const [searchTerm, setSearchTerm] = useState("");

  const filteredStudents = students.filter((student) =>
    student.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="section">
      <h2 className="section-title">Students</h2>
      <div className="search-input-wrapper">
        <input
          type="text"
          placeholder="Search students"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="search-input"
        />
      </div>
      <div className="course-student-list">
        <div className="row-header">
          <div className="student-name-wrapper">User</div>
          <div className="student-grade-wrapper">Grade</div>
          <div className="student-actions-wrapper">Actions</div>
        </div>
        {isLoading && <div className="table-row">Loading students...</div>}
        {error && <div className="table-row">Error: {error}</div>}
        {!isLoading && !error && filteredStudents.length === 0 && (
          <div className="table-row">No students found</div>
        )}
        {filteredStudents.map((student) => (
          <div key={student.id} className="table-row">
            <div className="student-name-wrapper">
              <div className="student-name">{student.name}</div>
              <div className="student-username">{student.username}</div>
            </div>
            <div className="student-grade-wrapper">
              <div className="student-grade">{student.grade || "-"}</div>
            </div>
            <div className="student-actions-wrapper">
              <button
                className="base-button delete-button"
                onClick={() => onDeleteStudent(student.id)}
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

const AssignmentList = ({ assignments, isLoading, error }) => {
  return (
    <div className="section">
      <h2 className="section-title">Assignments</h2>
      {isLoading && <div>Loading assignments...</div>}
      {error && <div>Error: {error}</div>}
      <div className="course-assignment-list">
        <div className="row-header">
          <div className="assignment-title-wrapper">Title</div>
          <div className="assignment-average-grade-wrapper">Grade</div>
        </div>
        {assignments.map((assignment) => (
          <div key={assignment.id} className="table-row">
            <div className="assignment-title-wrapper">
              <div className="assignment-title">{assignment.title}</div>
            </div>
            <div className="assignment-average-grade-wrapper">
              <div className="assignment-average-grade">
                {assignment.averageGrade || "-"}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CourseDetailsView;