import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import './StudentStyle.css';
import axios from "axios";

const StudentPage = () => {
    const navigate = useNavigate();
    const userName = localStorage.getItem("username")

    return (
        <div className="dashboard-container">
            <header className="header">
                <h1 className="header-title">Student Dashboard</h1>
                <div className="header-buttons">
                    <button className="btn" onClick={() => navigate('/studentAssignments')}>Assignments</button>
                    <button className="btn" onClick={() => navigate(`/my-courses-student/${userName}`)}>My Courses</button>
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

const Assignments = ( ) => {
    const [assignments, setAssignments] = useState([]);
    const [submissionStatus, setSubmissionStatus] = useState([])
    const [searchQuery, setSearchQuery] = useState('');
    const userName = localStorage.getItem("username")

    useEffect(() => {
        const fetchAssignments = async () => {
            try {
                const response = await axios.get('http://localhost:8080/assignments');
                setAssignments(response.data);

                const statusPromises = response.data.map(async (assignment) => {
                    const statusResponse = await axios.get(`http://localhost:8080/student/${userName}/assignments/${assignment.assignmentId}`);
                    return { assignmentId: assignment.assignmentId, status: statusResponse.data};
                });

                const statuses = await Promise.all(statusPromises);
                const statusMap = statuses.reduce((map, item) => {
                    map[item.assignmentId] = item.status ? "Yes" : "No";
                    return map;
                }, {});

                setSubmissionStatus(statusMap)

            } catch (error) {
                console.error('Error fetching assignments:', error);
            }
        };

        fetchAssignments();
    }, [userName]);

    const filteredAssignments = assignments.filter((assignment) =>
        assignment.assignmentName.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="assignments-section">
            <h2 className="assignments-title">Current Assignments</h2>
            <div className="search-container">
                <input
                    type="text"
                    placeholder="Search assignments by name..."
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    className="search-input"
                />
            </div>
            <ul className="assignments-list">
                {filteredAssignments.length > 0 ? (
                    filteredAssignments.map((assignment) => (
                        <li className="assignment-item" key={assignment.assignmentId}>
                            {assignment.assignmentName} - Due: {assignment.dueDate} - Submitted: {submissionStatus[assignment.assignmentId] || 'No'}
                        </li>
                    ))
                ) : (
                    <li className="assignment-item no-assignments">No assignments match your search</li>
                )}
            </ul>
        </div>
    );
};

export default StudentPage;
