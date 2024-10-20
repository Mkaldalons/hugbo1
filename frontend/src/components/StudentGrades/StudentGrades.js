import React, { useState, useEffect } from 'react';
import './StudentGrades.css'; // Add your styles here if necessary
import { useNavigate } from 'react-router-dom';

function StudentGrades({ username }) {
    const [assignments, setAssignments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch assignments and grades for the student from the backend
        const fetchAssignments = async () => {
            try {
                const response = await fetch(`/students/${username}/assignments`);
                if (!response.ok) {
                    throw new Error('Error fetching assignments');
                }
                const data = await response.json();
                setAssignments(data);
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        if (username) {
            fetchAssignments();
        }
    }, [username]);

    const handleGoBack = () => {
        navigate('/');
    };

    return (
        <div className="student-assignments-page">
            <h1>{username}'s Assignments and Grades</h1>
            {loading && <p>Loading assignments...</p>}
            {error && <p>Error: {error}</p>}

            {!loading && assignments.length === 0 && (
                <p>No assignments found for you.</p>
            )}

            {!loading && assignments.length > 0 && (
                <ul className="assignments-list">
                    {assignments.map((assignment) => (
                        <li key={assignment.name} className="assignment-item">
                            {assignment.name}: {assignment.grade !== null ? assignment.grade : 'Not Graded'}
                        </li>
                    ))}
                </ul>
            )}

            <button className="go-back-btn" onClick={handleGoBack}>Go Back</button>
        </div>
    );
}

export default StudentGrades;
