import './Assignment.css';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Assignment = () => {
    const [courses, setCourses] = useState([]);
    const [selectedCourseId, setSelectedCourseId] = useState("");
    const [assignmentName, setAssignmentName] = useState("");
    const [questions, setQuestions] = useState([]);
    const [newQuestion, setNewQuestion] = useState("");
    const [newOptions, setNewOptions] = useState(["", "", "", ""]);
    const [correctAnswerIndex, setCorrectAnswerIndex] = useState(null);
    const [dueDate, setDueDate] = useState("");
    const [message, setMessage] = useState("");

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

    const addQuestion = () => {
        if (newQuestion.trim() && newOptions.every(option => option.trim()) && correctAnswerIndex !== null) {
            const newQuestionObject = {
                question: newQuestion,
                options: [...newOptions],
                correctAnswer: newOptions[correctAnswerIndex]
            };

            setQuestions([...questions, newQuestionObject]);

            setNewQuestion("");
            setNewOptions(["", "", "", ""]);
            setCorrectAnswerIndex(null);
        } else {
            alert('Please fill out the question, options, and select a correct answer.');
        }
    };

    const submitAssignment = async () => {
        if (!selectedCourseId) {
            alert('Please select a course.');
            return;
        }
        if (!dueDate) {
            alert('Please select a due date.');
            return;
        }

        const assignmentData = {
            courseId: selectedCourseId,
            dueDate: dueDate,
            questionRequests: questions,
            assignmentName: assignmentName
        };


        try {
            const response = await axios.post('http://localhost:8080/create', assignmentData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            console.log('Assignment created successfully:', response.data);
            setMessage("Assignment created successfully");
        } catch (error) {
            console.error('Error submitting assignment:', error);
        }
    };

    return (
        <div className="quiz-container">
            <h2>Create New Assignment</h2>

            <div className="input-section">
                <label>Select Course:</label>
                <select
                    value={selectedCourseId}
                    onChange={(e) => setSelectedCourseId(e.target.value)}
                >
                    <option value="">Select a course</option>
                    {courses.map((course) => (
                        <option key={course.courseId} value={course.courseId}>
                            {course.courseName}
                        </option>
                    ))}
                </select>
            </div>

            <div className="input-section">
                <label>Assignment Name:</label>
                <input
                    type="text"
                    value={assignmentName}
                    onChange={(e) => setAssignmentName(e.target.value)}
                />
            </div>

            <div className="input-section">
                <label>Due Date:</label>
                <input
                    type="date"
                    value={dueDate}
                    onChange={(e) => setDueDate(e.target.value)}
                />
            </div>

            <div className="input-section">
                <h3>Add Questions</h3>
                <div>
                    <label>Question:</label>
                    <input
                        type="text"
                        value={newQuestion}
                        onChange={(e) => setNewQuestion(e.target.value)}
                    />
                </div>
                <div>
                    <label>Options:</label>
                    {newOptions.map((option, index) => (
                        <div key={index} className="option-checkbox">
                            <input
                                type="text"
                                value={option}
                                onChange={(e) => {
                                    const updatedOptions = [...newOptions];
                                    updatedOptions[index] = e.target.value;
                                    setNewOptions(updatedOptions);
                                }}
                                placeholder={`Option ${index + 1}`}
                            />
                            <input
                                type="checkbox"
                                checked={correctAnswerIndex === index}
                                onChange={() => setCorrectAnswerIndex(index)}
                            />
                        </div>
                    ))}
                </div>
                <button className="add-question-btn" onClick={addQuestion}>
                    Add Question
                </button>
            </div>

            <h3>Questions Added: {questions.length}</h3>
            <ul>
                {questions.map((q, index) => (
                    <li key={index}>
                        {q.question} (Correct: {q.correctAnswer})
                    </li>
                ))}
            </ul>

            <button className="start-quiz-btn" onClick={submitAssignment}>
                Submit Assignment
            </button>
        </div>
    );
};

export default Assignment;
