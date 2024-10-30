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
                    <button className="btn" onClick={() => navigate('/studentAssignments')}>Assignments</button>
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

const Assignments = ( ) => {
    const [assignments, setAssignments] = useState([]);
    const [submissionStatus, setSubmissionStatus] = useState([])
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

    return (
        <div className="assignments-section">
            <h2 className="assignments-title">Current Assignments</h2>
            <ul className="assignments-list">
                {assignments.length > 0 ? (
                    assignments.map((assignment) => (
                        <li className="assignment-item" key={assignment.assignmentId}>
                            {assignment.assignmentName} - Due: {assignment.dueDate} - Sumbitted: {submissionStatus[assignment.assignmentId]}
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
