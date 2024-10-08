import React from 'react';
import './Instructor.css';

const Instructor = () => {
    return (
        <div className="dashboard-container">
            <header>
                <h1>Instructor Dashboard</h1>
                <div className="buttons">
                    <button onClick={() => alert('Create Assignment feature coming soon!')}>Create Assignment</button>
                    <button onClick={() => alert('View Students feature coming soon!')}>View Students</button>
                    <button onClick={() => alert('View Grades feature coming soon!')}>View Grades</button>
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

const Assignments = () => (
    <div className="assignments-section">
        <h2>Current Assignments</h2>
        <ul>
            <li>Assignment 1 - Due: 12/10/2024 - Status: Pending</li>
            <li>Assignment 2 - Due: 12/20/2024 - Status: Grading</li>
        </ul>
    </div>
);

export default Instructor;