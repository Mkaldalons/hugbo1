import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Instructor.css';
import axios from 'axios';

const Instructor = () => {
    const navigate = useNavigate();

    return (
        <div className="dashboard-container">
            <header>
                <h1>Instructor Dashboard</h1>
                <div className="buttons">
                    <button onClick={() => navigate('/course')}>Create Course</button>
                    <button onClick={() => navigate('/assignment')}>Create Assignment</button>
                    <button onClick={() => alert('/studentView')}>View Students</button>
                </div>
            </header>

            <main>
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
                const response = await axios.get('http://localhost:8080/assignments'); // Adjust URL based on your backend
                setAssignments(response.data); // Assuming response.data contains an array of assignments
            } catch (error) {
                console.error('Error fetching assignments:', error);
            }
        };

        fetchAssignments();
    }, []);

    return (
        <div className="assignments-section">
            <h2>Current Assignments</h2>
            <ul>
                {assignments.length > 0 ? (
                    assignments.map((assignment) => (
                        <li key={assignment.id}>
                            {assignment.name} - Due: {assignment.dueDate} - Status: {assignment.status || 'Pending'}
                        </li>
                    ))
                ) : (
                    <li>No assignments available</li>
                )}
            </ul>
        </div>
    );
};

export default Instructor;
