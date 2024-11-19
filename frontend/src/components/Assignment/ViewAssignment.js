import React, { useEffect, useState } from "react";
import axios from 'axios';
import { useParams } from 'react-router-dom';

const ViewAssignment = () => {
        const { assignmentId } = useParams();
        const [assignment, setAssignment] = useState(null);
        const [loading, setLoading] = useState(true);
        const [error, setError] = useState(null);
        const [averageGrade, setAverageGrade] = useState(null);
        const [grades, setGrades] = useState([]);


    useEffect(() => {
            const fetchAssignment = async () => {
                try {
                    const response = await axios.get(`http://localhost:8080/assignment/${assignmentId}`);
                    const data = response.data;
                    setAssignment(data);

                    const averageResponse = await axios.get(`http://localhost:8080/average-grade/${assignmentId}`);
                    const averageGradeData = averageResponse.data;
                    setAverageGrade(averageGradeData)

                    const allGradesResponse = await axios.get(`http://localhost:8080/grades/${assignmentId}`)
                    const grades = allGradesResponse.data;
                    setGrades(grades);

                } catch (err) {
                    setError(err.message);
                } finally {
                    setLoading(false);
                }
            };

            fetchAssignment();
        }, [assignmentId]);

        if (loading) return <p>Loading...</p>;
        if (error) return <p>Error: {error}</p>;

        return (
            <div>
                <h1>Assignment Details</h1>
                {assignment && (
                    <div>
                        <p><strong>ID:</strong> {assignment.assignmentId}</p>
                        <p><strong>Name:</strong> {assignment.assignmentName}</p>
                        <p><strong>Due Date:</strong> {assignment.dueDate}</p>
                        <p><strong>Published:</strong> {assignment.published ? "Yes" : "No"}</p>
                        {/* Add other attributes as needed */}
                    </div>
                )}
                <h2>Grades</h2>
                {averageGrade !== null && <p><strong>Average Grade:</strong> {averageGrade}</p>}
                {grades.length > 0 ? (
                    <ul>
                        {grades.map((grade, index) => (
                            <li key={index}>Grade {index + 1}: {grade}</li>
                        ))}
                    </ul>
                ) : (
                    <p>No grades available for this assignment.</p>
                )}
            </div>
        );
};

export default ViewAssignment;