import React, {useEffect, useState} from 'react';
import './Course.css';
import axios from "axios";// Updated to link the new CSS file

const Course = () => {
    const [courseName, setCourseName] = useState("");
    const [courses, setCourses] = useState([]);

    const createCourse = async () => {
        if (courseName.trim()) {
            const username = localStorage.getItem('username');
            const newCourse = { courseName: courseName, createdBy: username };
            if(!username){
                console.error('No username found.')
                return;
            }

            try {
                const response = await axios.post('http://localhost:8080/courses', newCourse);
                setCourses([...courses, response.data]); // Add the new course to the state
                setCourseName(""); // Reset the course name input
            } catch (error) {
                console.error('Error creating course:', error);
            }
        }
    };

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await axios.get('http://localhost:8080/courses');
                setCourses(response.data);
            } catch (error) {
                console.error('Error fetching courses:', error);
            }
        };
        fetchCourses();
    }, []);


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
                        <li key={course.courseId}>{course.courseName}</li>
                    ))
                ) : (
                    <li>No courses available</li>
                )}
            </ul>
        </div>
    );
};

export default Course;
