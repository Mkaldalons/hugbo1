import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './StudentStyle.css';
import axios from "axios";

const StudentPage = () => {
    const navigate = useNavigate();

    return (
        <div className="dashboard-container">
            <header className="header">
                <h1 className="header-title">Student Dashboard</h1>
                <div className="header-buttons">
                    <button className="btn" onClick={() => navigate('/studentAssignments')}>Assignment</button>
                    <button className="btn" onClick={() => navigate('/studentGrades')}>View Grades</button>
                </div>
            </header>

            <main className="main-content">
                <div className="content-container">
                    <Assignments />
                </div>
            </main>
        </div>
    );
};

const Assignments = () => {
    const [assignments, setAssignments] = useState([]);

    useEffect(() => {
        const fetchAssignments = async () => {
            try {
                const response = await axios.get('http://localhost:8080/assignments');
                setAssignments(response.data);
            } catch (error) {
                console.error('Error fetching assignments:', error);
            }
        };

        fetchAssignments();
    }, []);

    return (
        <div className="assignments-section">
            <h2 className="assignments-title">Current Assignments</h2>
            <ul className="assignments-list">
                {assignments.length > 0 ? (
                    assignments.map((assignment) => (
                        <li className="assignment-item" key={assignment.id}>
                            {assignment.name} - Due: {assignment.dueDate} - Status: {assignment.status || 'Pending'}
                        </li>
                    ))
                ) : (
                    <li className="assignment-item no-assignments">No assignments available</li>
                )}
            </ul>
        </div>
    );
};

export default StudentPage;
