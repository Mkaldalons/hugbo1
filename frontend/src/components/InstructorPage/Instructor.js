import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Instructor.css';
import axios from 'axios';
import AverageGrades from './AverageGrades';

const Instructor = () => {
    const navigate = useNavigate();
    localStorage.clear();
    sessionStorage.clear();

    return (
        <div className="dashboard-container">
            <header>
                <h1>Instructor Dashboard</h1>
                <div className="buttons">
                    <button onClick={() => navigate('/course')}>Create Course</button>
                    <button onClick={() => navigate('/assignment')}>Create Assignment</button>
                    <button onClick={() => navigate('/studentView')}>View Students</button>
                    <button onClick={() => navigate('/courseView')}>View My Courses</button>
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
    const [searchQuery, setSearchQuery] = useState('');
    const navigate = useNavigate();
    const [message, setMessage] = useState("");

    const fetchAssignments = async () => {
        try {
            const response = await axios.get('http://localhost:8080/assignments');
            setAssignments(response.data);
        } catch (error) {
            console.error('Error fetching assignments:', error);
        }
    };

    useEffect(() => {
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
                    setAssignments(assignments.filter((assignment) => assignment.assignmentId !== assignmentId));
                    console.log("Assignment published value", assignments.published)
                }
            } catch (error) {
                console.error('Error deleting assignment:', error);
            }
        }
    };

    const handlePublish = async (assignmentId, published) => {
        try {
            let response;
            console.log("The assignment you are interacting with has the status: ", published)
            if (!published) {
                response = await axios.post(`http://localhost:8080/publish-assignment/${assignmentId}`);
                if (response.data.message === 'Assignment published') {
                    await fetchAssignments();
                    setMessage("Assignment successfully published");
                    console.log(message)
                } else {
                    setMessage("Failed to publish assignment");
                    console.log(message)
                }
            } else {
                response = await axios.post(`http://localhost:8080/unpublish-assignment/${assignmentId}`);
                if (response.data.message === 'Assignment unpublished') {
                    await fetchAssignments();
                    setMessage("Assignment successfully unpublished");
                } else {
                    setMessage("Failed to unpublish assignment");
                }
            }
        } catch (error) {
            console.error("Error publishing/unpublishing assignment:", error);
            setMessage("An error occurred. Please try again later.");
        }
    };

    function isPublished(assignment) {
        return assignment.published ? "Yes" : "No"
    }

    const filteredAssignments = assignments.filter((assignment) =>
        assignment.assignmentName.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="assignments-section">
            <h2>Current Assignments</h2>
            <div className="search-container">
                <input
                    type="text"
                    placeholder="Search assignments by name..."
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                />
            </div>
            <ul>
                {filteredAssignments.length > 0 ? (
                    filteredAssignments.map((assignment) => (
                        <li key={assignment.assignmentId}>
                            {assignment.assignmentName} - Due: {assignment.dueDate} - Published: {isPublished(assignment)} - <AverageGrades assignmentId={assignment.assignmentId} />
                            <button onClick={() => handleEdit(assignment.assignmentId)}>Edit</button>
                            <button onClick={() => handleDelete(assignment.assignmentId)}>Delete</button>
                            <button onClick={() => handlePublish(assignment.assignmentId, assignment.published)}>{assignment.published ? "Unpublish" : "Publish"}</button>
                        </li>
                    ))
                ) : (
                    <li>No assignments match your search</li>
                )}
            </ul>
        </div>
    );
};


export default Instructor;
