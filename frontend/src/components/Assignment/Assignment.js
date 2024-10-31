import './Assignment.css';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Assignment = () => {
    const [courses, setCourses] = useState([]);
    const [selectedCourseId, setSelectedCourseId] = useState("");
    const [questions, setQuestions] = useState([]);
    const [newQuestion, setNewQuestion] = useState("");
    const [newOptions, setNewOptions] = useState(["", "", "", ""]);
    const [correctAnswerIndex, setCorrectAnswerIndex] = useState(null);
    const [dueDate, setDueDate] = useState("");
    const [searchTerm, setSearchTerm] = useState("");
    const [searchResults, setSearchResults] = useState([]);

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

        // Create the JSON object to represent the questions
        const assignmentData = {
            courseId: selectedCourseId,
            dueDate: dueDate,
            questionRequests: questions
        };


        try {
            // Send the data as a JSON string in the request body
            const response = await axios.post('http://localhost:8080/create', assignmentData, {
                headers: {
                    'Content-Type': 'application/json',  // Ensure the correct content type
                },
            });
            console.log('Assignment created successfully:', response.data);
            console.log(JSON.stringify(assignmentData, null, 2));
        } catch (error) {
            console.error('Error submitting assignment:', error);
        }
    };

    const handleSearch = async () => {
        if (searchTerm.trim()) {
            try {
                const encodedSearchTerm = encodeURIComponent(searchTerm.trim());
                const response = await axios.get(`http://localhost:8080/api/assignments/search?name=${encodedSearchTerm}`);
                console.log('Search response:', response.data);
                setSearchResults(response.data);
            } catch (error) {
                console.error('Error searching assignments:', error);
            }
        }
    };
    return (
        <div className="quiz-container">
            <h2>Create New Assignment</h2>

            {/* Search bar for assignments */}
            <div className="input-section">
                <label>Search Assignments:</label>
                <input
                    type="text"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    placeholder="Enter assignment name"
                />
                <button onClick={handleSearch}>Search</button>
            </div>

            {/* Display search results */}
            <div className="search-results">
                <h3>Search Results:</h3>
                <ul>
                    {searchResults.length > 0 ? (
                        searchResults.map((assignment) => (
                            <li key={assignment.id}>{assignment.assignmentName}</li>
                        ))
                    ) : (
                        <p>No assignments found.</p>
                    )}
                </ul>
            </div>

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
