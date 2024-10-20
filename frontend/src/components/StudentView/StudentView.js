import React, { useState, useEffect } from 'react';
import './StudentView.css';

const StudentView = () => {
    const [courses, setCourses] = useState([]);
    const [selectedCourse, setSelectedCourse] = useState('');
    const [userName, setUserName] = useState('');
    const [students, setStudents] = useState([])

    // Fetch the available courses when the component loads
    useEffect(() => {
        fetch('http://localhost:8080/courses')  // Adjust your backend URL as needed
            .then(response => response.json())
            .then(data => setCourses(data))  // Set the list of courses
            .catch(error => console.error('Error fetching courses:', error));
    }, []);

    const fetchStudents = (courseId) => {
        fetch(`http://localhost:8080/students?courseId=${courseId}`)
            .then(response => response.json())
            .then(data => setStudents(data))
            .catch(error => console.error('Error fetching students: ', error));
    };


    const handleCourseChange = (event) => {
        const courseId = event.target.value;
        fetchStudents(courseId);
    }

    const handleAddStudent = (event) => {
        event.preventDefault();  // Prevent the default form submission
        if (!selectedCourse || !userName) {
            alert('Please select a course and enter a username');
            return;
        }

        fetch('http://localhost:8080/students/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userName: userName, courseId: selectedCourse }),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Response from backend:', data);
                alert('Student added successfully!');
                setUserName('');  // Clear the input field after successful submission
            })
            .catch(error => console.error('Error adding student:', error));
    };

    return (
        <div className="container">
            <h1>Add Student to Course</h1>

            {/* Dropdown for selecting a course */}
            <select className="dropdown" value={selectedCourse} onChange={handleCourseChange}>
                <option value="">Select a course</option>
                {courses.map(course => (
                    <option key={course.courseId} value={course.courseId}>
                        {course.courseName}
                    </option>
                ))}
            </select>

            {/* Form to add a student */}
            <form className="form" onSubmit={handleAddStudent}>
                <input
                    className="input"
                    type="text"
                    placeholder="Enter student username"
                    value={userName}
                    onChange={(e) => setUserName(e.target.value)}
                />
                <button className="button" type="submit">Add Student</button>
            </form>

            {/* Display list of students*/}
            <ul className="list">
                {students.map(student => (
                    <li className="list-item" key={student.userName}>{student.userName}</li>
                ))}
            </ul>
        </div>
    );
};

export default StudentView;
