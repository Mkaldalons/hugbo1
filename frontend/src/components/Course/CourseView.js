import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './CourseView.css'
import { useNavigate } from 'react-router-dom';

const CourseView = () => {
    const[courses, setCourses] = useState([]);
    const[loading, setLoading] = useState(true);
    const[error, setError] = useState(null);

    const userName = localStorage.getItem("username");

    const navigate = useNavigate();

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

    const handleViewCourseClick = (courseId) => {
        navigate(`/courseView/${courseId}`);
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
                        <button onClick={() => handleViewCourseClick(course.courseId)}>
                            View Course
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CourseView;