import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Instructor.css';
import axios from 'axios';
import AverageGrades from './AverageGrades';

const Instructor = () => {
    const navigate = useNavigate();

    return (
        <div className="dashboard-container">
            <header>
                <h1>Instructor Dashboard</h1>
                <div className="buttons">
                    <button onClick={() => navigate('/course')}>Create Course</button>
                    <button onClick={() => navigate('/assignment')}>Create Assignment</button>
                    <button onClick={() => navigate('/studentView')}>View Students</button>
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
    const navigate = useNavigate();

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

    const handleEdit = (assignmentId) => {
        navigate(`/edit-assignment/${assignmentId}`);
    };

    const handleDelete = async (assignmentId) => {
        if (window.confirm('Are you sure you want to delete this assignment?')) {
            try {
                const response = await axios.post('http://localhost:8080/delete-assignment', {
                    assignmentId: assignmentId
                });
                if (response.data.message === 'Assignment deleted') {
                    // Remove the deleted assignment from the assignments list in state
                    setAssignments(assignments.filter((assignment) => assignment.assignmentId !== assignmentId));
                }
            } catch (error) {
                console.error('Error deleting assignment:', error);
            }
        }
    };

    return (
        <div className="assignments-section">
            <h2>Current Assignments</h2>
            <ul>
                {assignments.length > 0 ? (
                    assignments.map((assignment) => (
                        <li key={assignment.assignmentId}>
                            {assignment.assignmentName} - Due: {assignment.dueDate} - < AverageGrades assignmentId={assignment.assignmentId}/>
                            <button onClick={() => handleEdit(assignment.assignmentId)}>Edit</button>
                            <button onClick={() => handleDelete(assignment.assignmentId)}>Delete</button>
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
