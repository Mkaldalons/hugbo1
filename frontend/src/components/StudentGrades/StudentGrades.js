import React, { useState, useEffect } from 'react';
import './StudentGrades.css';
import { useNavigate } from 'react-router-dom';
import axios from "axios";

async function fetchAllGrades(userName) {
    try {
        const response = await axios.get(`http://localhost:8080/assignment-submissions-grades/${userName}`);
        return response.data;
        console.log(response.data)
    } catch (error) {
        console.error(`Error fetching grades for user ${userName}:`, error);
        return [];
    }
}

async function fetchAverageGrade(userName) {
    try {
        const response = await axios.get(`http://localhost:8080/average-grade-student/${userName}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching average grade for user ${userName}:`, error);
        return null;
    }
}

function StudentGrades() {
    const [gradesList, setGradesList] = useState([]);
    const [averageGrade, setAverageGrade] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const userName = localStorage.getItem("username");

    useEffect(() => {
        const fetchGrades = async () => {
            try {
                const grades = await fetchAllGrades(userName);
                setGradesList(grades);
            } catch (error) {
                setError("Error fetching grades.");
            } finally {
                setLoading(false);
            }
        };

        if (userName) {
            fetchGrades();
        }
    }, [userName]);

    useEffect(() => {
        const getAverageGrade = async () => {
            const average = await fetchAverageGrade(userName);
            setAverageGrade(average);
        };

        if (userName) {
            getAverageGrade();
        }
    }, [userName]);

    const handleGoBack = () => {
        navigate('/');
    };

    return (
        <div className="student-assignments-page">
            <h1>{userName}'s Assignments and Grades</h1>
            {loading && <p>Loading assignments...</p>}
            {error && <p>Error: {error}</p>}

            {!loading && gradesList.length === 0 && (
                <p>No assignments found for you.</p>
            )}

            {!loading && gradesList.length > 0 && (
                <ul className="assignments-list">
                    {gradesList.map((gradeItem) => (
                        <li key={gradeItem.assignmentId} className="assignment-item">
                            {gradeItem.assignmentName} - Grade: {gradeItem.assignmentGrade ?? 'No grade available'}
                        </li>
                    ))}
                </ul>
            )}

            {!loading && (
                <div className="average-grade">
                    <h2>Average Grade:</h2>
                    <p>{averageGrade !== null ? averageGrade.toFixed(2) : "No grades available"}</p>
                </div>
            )}

            <button className="go-back-btn" onClick={handleGoBack}>Go Back</button>
        </div>
    );
}

export default StudentGrades;

