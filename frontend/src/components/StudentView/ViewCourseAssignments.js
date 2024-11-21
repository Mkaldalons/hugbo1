import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";

const ViewCourseAssignments = () => {
    const { courseId } = useParams();
    const navigate = useNavigate();

    const [assignments, setAssignments] = useState([]);
    const [submissions, setSubmissions] = useState({});
    const [averageGrade, setAverageGrade] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const userName = localStorage.getItem("username");

    useEffect(() => {
        const fetchAssignmentsAndSubmissions = async () => {
            try {
                const assignmentResponse = await axios.get(
                    `http://localhost:8080/assignments-by-course/${courseId}`
                );
                const assignmentsData = assignmentResponse.data;
                setAssignments(assignmentsData);

                const submissionsData = {};
                for (const assignment of assignmentsData) {
                    const submissionResponse = await axios.get(
                        `http://localhost:8080/assignment-submissions/${assignment.assignmentId}`,
                        {
                            params: { userName },
                        }
                    );
                    submissionsData[assignment.assignmentId] = submissionResponse.data;
                }
                setSubmissions(submissionsData);
            } catch (err) {
                if (err.response && err.response.status === 404) {
                    setAssignments([]);
                } else {
                    setError("An unexpected error occurred.");
                }
            } finally {
                setLoading(false);
            }
        };

        const fetchAverageGrade = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8080/average-grade-student-course/${courseId}`,
                    {
                        params: { userName },
                    }
                );
                setAverageGrade(response.data); // Update average grade
            } catch (err) {
                console.error("Failed to fetch average grade:", err);
                setAverageGrade(null);
            }
        };

        fetchAssignmentsAndSubmissions();
        fetchAverageGrade(); // Fetch average grade
    }, [courseId, userName]);

    const handleTakeAssignment = (assignmentId) => {
        navigate(`/take-assignment/${assignmentId}`);
    };

    const isDueDatePassed = (dueDate) => {
        const currentDate = new Date();
        const assignmentDueDate = new Date(dueDate);
        return currentDate > assignmentDueDate;
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            <h1>Assignments for Course {courseId}</h1>
            {/* Display the average grade */}
            <p>
                <strong>Average Grade for Course:</strong>{" "}
                {averageGrade !== null ? averageGrade.toFixed(2) : "No grades available"}
            </p>
            {assignments.length > 0 ? (
                <ul>
                    {assignments.map((assignment) => (
                        <li key={assignment.assignmentId}>
                            <h2>{assignment.assignmentName}</h2>
                            <p><strong>Due Date:</strong> {assignment.dueDate}</p>
                            {isDueDatePassed(assignment.dueDate) ? (
                                <p style={{ color: "red" }}>Cannot submit assignment - due date has passed</p>
                            ) : (
                                <>
                                    <ul>
                                        {submissions[assignment.assignmentId]?.length > 0 ? (
                                            submissions[assignment.assignmentId].map((grade, index) => (
                                                <li key={index}>
                                                    Grade: {grade}
                                                </li>
                                            ))
                                        ) : (
                                            <li>No submissions yet</li>
                                        )}
                                    </ul>
                                    <button onClick={() => handleTakeAssignment(assignment.assignmentId)}>
                                        {submissions[assignment.assignmentId]?.length > 0 ? "Resubmit" : "Submit"}
                                    </button>
                                </>
                            )}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No assignments found for this course.</p>
            )}
        </div>
    );
};

export default ViewCourseAssignments;

