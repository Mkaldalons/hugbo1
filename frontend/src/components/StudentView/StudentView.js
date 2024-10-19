import React, { useState, useEffect } from 'react';
import './StudentView.css';

const StudentView = () => {
    const [courses, setCourses] = useState([]);
    const [selectedCourse, setSelectedCourse] = useState('');
    const [students, setStudents] = useState([]);
    const [username, setUsername] = useState('');

    useEffect(() => {
        // Fetch all courses from the backend
        fetch('/courses')
            .then(response => response.json())
            .then(data => setCourses(data))
            .catch(error => console.error('Error fetching courses:', error));
    }, []);

    const fetchStudents = (courseId) => {
        setSelectedCourse(courseId);
        fetch(`/students?courseId=${courseId}`)
            .then(response => response.json())
            .then(data => setStudents(data))
            .catch(error => console.error('Error fetching students:', error));
    };

    const handleCourseChange = (event) => {
        const courseId = event.target.value;
        fetchStudents(courseId);
    };

    const handleAddStudent = (event) => {
        event.preventDefault();
        if (!selectedCourse) {
            alert('Please select a course first!');
            return;
        }

        // Send the new student username to the backend
        fetch(`/students/add`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, courseId: selectedCourse }),
        })
            .then(response => response.json())
            .then(data => {
                alert('Student added successfully!');
                setUsername('');
                fetchStudents(selectedCourse); // Refresh the student list
            })
            .catch(error => console.error('Error adding student:', error));
    };

    return (
        <div className="container">
            <h1 className="header">Course Student Overview</h1>

            {/* Dropdown for selecting course */}
            <select className="dropdown" value={selectedCourse} onChange={handleCourseChange}>
                <option value="">Select a course</option>
                {courses.map(course => (
                    <option key={course.id} value={course.id}>
                        {course.name}
                    </option>
                ))}
            </select>

            {/* Form to add new student */}
            <form className="form" onSubmit={handleAddStudent}>
                <input
                    className="input"
                    type="text"
                    placeholder="Enter student username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <button className="button" type="submit">Add Student</button>
            </form>

            {/* Display list of students */}
            <ul className="list">
                {students.map(student => (
                    <li className="list-item" key={student.username}>{student.username}</li>
                ))}
            </ul>
        </div>
    );
};


export default StudentView;
