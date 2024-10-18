import React, { useState } from 'react';
import './Course.css';
import axios from "axios";

const Course = () => {
    const [courseName, setCourseName] = useState("");
    const [courses, setCourses] = useState([]);

    const createCourse = async () => {
        if (courseName.trim()) {
            const newCourse = { name: courseName };

            try {
                const response = await axios.post('http://localhost:8080/courses', newCourse);
                setCourses([...courses, response.data]);
                setCourseName("");
            } catch (error) {
                console.error('Error creating course:', error);
            }
        }
    };

    return (
        <div className="course-container">
            <h2>Create a New Course</h2>

            <div className="input-section">
                <label>Course Name:</label>
                <input
                    type="text"
                    value={courseName}
                    onChange={(e) => setCourseName(e.target.value)}
                    placeholder="Enter course name"
                />
            </div>
            <button className="create-course-btn" onClick={createCourse}>Create Course</button>

            <h3>Courses</h3>
            <ul>
                {courses.length > 0 ? (
                    courses.map((course) => (
                        <li key={course.id}>{course.name}</li>
                    ))
                ) : (
                    <li>No courses available</li>
                )}
            </ul>
        </div>
    );
};

export default Course;
