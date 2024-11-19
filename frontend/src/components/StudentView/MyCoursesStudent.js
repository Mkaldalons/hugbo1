import React, { useEffect, useState } from "react";
import axios from 'axios';
import { useParams } from 'react-router-dom';

const MyCoursesStudent = () => {
    const { userName } = useParams();
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
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

        fetchCourses();
    }, [userName]); // Fetch courses whenever userName changes

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
