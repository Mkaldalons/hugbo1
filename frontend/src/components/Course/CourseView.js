import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './CourseView.css'

const CourseView = () => {
    const[courses, setCourses] = useState([]);
    const[loading, setLoading] = useState(true);
    const[error, setError] = useState(null);

    const userName = localStorage.getItem("username");

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/my-courses/${userName}`);
                setCourses(response.data);
                setLoading(false);
            }catch (error) {
                console.error("Error fetching courses:", error);
                setError("Failed to load course");
                setLoading(false);
            }
        };

        if(userName) {
            fetchCourses();
        }else {
            setError("User not found.");
            setLoading(false);
        }
    }, [userName]);

    const handleViewCourse = (course) => {
        console.log("course viewed...")
    };

    return (
        <div className="course-view">
            <h1>{userName}'s Courses</h1>
            {loading && <p>Loading courses</p>}
            {error && <p>{error}</p>}

            {!loading && courses.length === 0 && <p>No courses found</p>}

            <ul className="course-list">
                {courses.map((course) => (
                    <li key={course.courseId} className="course-item">
                        <h3>{course.courseName}</h3>
                        <p>{course.description}</p>
                        <button onClick={() => handleViewCourse(course)}>
                            View Course
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CourseView;