import React, {useEffect, useState} from 'react';
import './Course.css';
import axios from "axios";// Updated to link the new CSS file


const CourseItem = ( { course } ) => {
    const [students, setStudents] = useState([]);
    const [loading, setLoading] = useState(false);
    const [hasFetched, setHasFetched] = useState(false);
    const[error, setError] = useState('')

    const onViewStudents = async (courseId) => {
        setLoading(true);

        const url = "http://localhost:8080/students?courseId=" + courseId
        await axios.get(url).then((response) => {
                setStudents(response.data);
        }).catch((error) => {
            console.log("error fetching students", error);
            setError("ekki náðist að sækja nemendur")
        }).finally(() => {
            setHasFetched(true);
            setLoading(false);
        });
    }

    return (
        <div key={course.id} className='courseItem'>
            <div className='courseItemContent'>
                
            <p>{course.courseName}</p>
            <button onClick={() => onViewStudents(course.courseId)}>Sjá nemendur</button>
            </div>
            {loading && <p className='courseItemLoader'>fetching students...</p>}
            {hasFetched && (
                <>
            <p>Students:</p>
            <div className='courseItemStudentList'>
                {students.map((student) => (
                    <p className='courseItemStudentListItem' key={student.studentId}>{student.name}</p>
                ))}
            </div>
            {error !== "" && <p className="courseItemLoader">{error}</p>}
            {students.length === 0 && <p className='courseItemLoader'>Engir nemendur fundust</p>}
                </>
            )}
        </div>
    )
}

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
                        <CourseItem key={course.courseId} course={course} />
                    ))
                ) : (
                    <li>No courses available</li>
                )}
            </ul>
        </div>
    );
};

export default Course;
