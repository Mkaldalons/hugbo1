import React, { useState, useEffect } from 'react';
import './StudentGrades.css';
import { useNavigate } from 'react-router-dom';
import axios from "axios";

async function fetchGrade(assignmentId, userName){
    try{
        const response = await axios.get(`http://localhost:8080/grade/${assignmentId}/student/${userName}`);
        return response.data;
    }catch (error){
        console.error(`Error fetching grade for assignment ${assignmentId} and username ${userName}`, error);
        return "No grades available";
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


function StudentGrades( ) {
    const [assignments, setAssignments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [averageGrade, setAverageGrade] = useState(null);
    const [error, setError] = useState(null);
    const [grades, setGrades] = useState({});
    const navigate = useNavigate();

    const userName = localStorage.getItem("username")

    useEffect(() => {
        const fetchAssignments = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/students-submitted-assignments/${userName}`);
                setAssignments(response.data);
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        if (userName) {
            fetchAssignments();
        }
    }, [userName]);

    const handleGoBack = () => {
        navigate('/');
    };

    useEffect(() => {
        const fetchGradesForAssignments = async () => {
            const gradesMap = {};
            for (const assignment of assignments){
                const grade = await fetchGrade(assignment.assignmentId, userName);
                gradesMap[assignment.assignmentId] = grade;
            }
            setGrades(gradesMap);
        };

        if(assignments.length > 0) {
            fetchGradesForAssignments();
        }
    }, [assignments, userName]);

    useEffect(() => {
        const getAverageGrade = async () => {
            const averageGrade = await fetchAverageGrade(userName);
            setAverageGrade(averageGrade);
        };

        if(userName){
            getAverageGrade();
        }
    }, [userName]);

    return (
        <div className="student-assignments-page">
            <h1>{userName}'s Assignments and Grades</h1>
            {loading && <p>Loading assignments...</p>}
            {error && <p>Error: {error}</p>}

            {!loading && assignments.length === 0 && (
                <p>No assignments found for you.</p>
            )}

            {!loading && assignments.length > 0 && (
                <ul className="assignments-list">
                    {assignments.map((assignment) => (
                        <li key={assignment.assignmentId} className="assignment-item">
                            {assignment.assignmentName} -Due: {assignment.dueDate} - Grade: {grades[assignment.assignmentId] ?? 'Loading...'}
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
