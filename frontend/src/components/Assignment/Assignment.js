import React, {useEffect, useState} from 'react';
import './Assignment.css';
import axios from 'axios';

const Assignment = () => {
    const [courses, setCourses] = useState([]);
    const [selectedCourseId, setSelectedCourseId] = useState("");
    const [questions, setQuestions] = useState([]);
    const [newQuestion, setNewQuestion] = useState("");
    const [newOptions, setNewOptions] = useState(["", "", "", ""]);
    const [correctAnswerIndex, setCorrectAnswerIndex] = useState(null);
    const [dueDate, setDueDate] = useState("");

    useEffect(() => {
        // Fetch the list of courses when the component loads
        const fetchCourses = async () => {
            try {
                const response = await axios.get('http://localhost:8080/courses');
                setCourses(response.data);
            } catch (error) {
                console.error('Error fetching courses:', error);
            }
        };
        fetchCourses();
    },[]);

    const addQuestion = async () => {
        if (newQuestion.trim() && newOptions.every((option) => option.trim()) && correctAnswerIndex !== null) {
            const newQuestionObject = {
                question: newQuestion,
                options: [...newOptions],
                correctAnswer: newOptions[correctAnswerIndex],
                dueDate
            };

            try {
                const response = await axios.post('http://localhost:8080/assignments', newQuestionObject);
                setQuestions([...questions, newQuestionObject]);
            } catch (error) {
                console.error('Error saving question:', error);
            }

            setNewQuestion("");
            setNewOptions(["", "", "", ""]);
            setCorrectAnswerIndex(null);
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
                        <option key={course.id} value={course.id}>
                            {course.name}
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

            <button className="start-quiz-btn">
                Submit Assignment
            </button>
        </div>
    );
};

export default Assignment;