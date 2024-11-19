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

    const handleEdit = (assignment, published) => {
        if (!published) {
            navigate(`/edit-assignment/${assignment}`)
        } else{
            setMessage("Assigments can not be edited after they have been published");
        }
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

    const handlePublish = async (assignmentId, published) => {
        try {
            let response;
            if (!published) {
                response = await axios.post(`http://localhost:8080/publish-assignment/${assignmentId}`);
                if (response.data.message === 'Assignment published') {
                    setMessage("Assignment successfully published");

                    setAssignments((prevAssignments) =>
                        prevAssignments.map((assignment) =>
                            assignment.assignmentId === assignmentId
                                ? { ...assignment, published: true }
                                : assignment
                        )
                    );
                } else {
                    setMessage("Failed to publish assignment");
                }
            } else {
                response = await axios.post(`http://localhost:8080/unpublish-assignment/${assignmentId}`);
                if (response.data.message === 'Assignment unpublished') {
                    setMessage("Assignment successfully unpublished");

                    // Update the published status locally
                    setAssignments((prevAssignments) =>
                        prevAssignments.map((assignment) =>
                            assignment.assignmentId === assignmentId
                                ? { ...assignment, published: false }
                                : assignment
                        )
                    );

                } else if (response.data.message === 'Assignment cannot be unpublished') {
                    setMessage('Cannot unpublish assignments while students are working on assignments');
                } else {
                    setMessage("Failed to unpublish assignment");
                }
            }
        }catch (error) {
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
                            <button onClick={() => navigate(`/view-assignment/${assignment.assignmentId}`)}>View</button>
                            {assignment.assignmentName} - Due: {assignment.dueDate} - Published: {isPublished(assignment)} - <AverageGrades assignmentId={assignment.assignmentId} />
                            <button onClick={() => handleEdit(assignment.assignmentId, assignment.published)}>Edit</button>
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
