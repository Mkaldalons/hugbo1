import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './StudentPage.css'; //

const StudentPage = () => {
    const [assignments, setAssignments] = useState([]);
    const [selectedAssignment, setSelectedAssignment] = useState(null);

    // Fetch assignments from the backend
    useEffect(() => {
        axios.get('http://localhost:8080/assignments')
            .then(response => {
                setAssignments(response.data);
            })
            .catch(error => {
                console.error('Error fetching the assignments:', error);
            });
    }, []);

    // Handle button click to view a specific assignment
    const handleViewAssignment = (assignment) => {
        setSelectedAssignment(assignment);
    };

    return (
        <div>
            <h1>Assignments</h1>
            <div className="assignment-list">
                {assignments.length > 0 ? (
                    <ul>
                        {assignments.map((assignment) => (
                            <li key={assignment.id}>
                                <h3>{assignment.title}</h3>
                                <p>Due Date: {new Date(assignment.dueDate).toLocaleDateString()}</p>
                                <button onClick={() => handleViewAssignment(assignment)}>
                                    View Assignment
                                </button>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No assignments available.</p>
                )}
            </div>

            {/* Show selected assignment details */}
            {selectedAssignment && (
                <div className="assignment-details">
                    <h2>{selectedAssignment.title}</h2>
                    <p>Due Date: {new Date(selectedAssignment.dueDate).toLocaleDateString()}</p>
                </div>
            )}
        </div>
    );
};

export default StudentPage;
