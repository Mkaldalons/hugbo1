import React, { useEffect, useState } from "react";
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

const MyCoursesStudent = () => {
    const { userName } = useParams();
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const fetchCourses = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/my-courses-student/${userName}`);
            setCourses(response.data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCourses();
    }, [userName]);

    const handleLeaveCourse = async (courseId) => {
        try {
            // Replace with the updated endpoint that matches the new logic
            await axios.delete(`http://localhost:8080/student/${userName}/courses/${courseId}`);
            
            // Update the state to reflect the removed course
            setCourses(courses.filter(course => course.id !== courseId));
            alert("Successfully left the course.");
            fetchCourses();
        } catch (err) {
            // Log the error for debugging and alert the user
            console.error("Error leaving course:", err.response || err.message);
            alert(err.response?.data?.message || "Failed to leave the course. Please try again.");
        }
    };


    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div>
            <h1>My Courses</h1>
            {courses.length > 0 ? (
                <ul>
                    {courses.map(course => (
                        <li key={course.courseId}>
                            <h2>{course.courseName}</h2>
                            <p>{course.description}</p>
                            <p><strong>Instructor:</strong> {course.instructor}</p>
                            <button
                                onClick={() => navigate(`/view-course-assignments/${course.courseId}`)}
                            >
                                View Course
                            </button>
                            <button onClick={() => handleLeaveCourse(course.courseId)}>Leave Course</button>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No courses found.</p>
            )}
        </div>
    );
};

export default MyCoursesStudent;
