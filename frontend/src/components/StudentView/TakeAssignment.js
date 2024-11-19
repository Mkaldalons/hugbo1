import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const TakeAssignment = () => {
    const { assignmentId } = useParams();
    const navigate = useNavigate();
    const [assignment, setAssignment] = useState(null);
    const [questions, setQuestions] = useState([]);
    const [answers, setAnswers] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [grade, setGrade] = useState(null);

    useEffect(() => {
        const fetchAssignmentDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/assignment/${assignmentId}`);
                setAssignment(response.data);

                // Parse the JSON data for questions
                const parsedQuestions = JSON.parse(response.data.jsonData);
                setQuestions(parsedQuestions); // Set the parsed questions
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchAssignmentDetails();
    }, [assignmentId]);

    const handleAnswerChange = (questionIndex, selectedAnswer) => {
        setAnswers((prevAnswers) => ({
            ...prevAnswers,
            [questionIndex]: selectedAnswer,
        }));
    };

    const handleSubmit = async () => {
        try {
            const studentAnswers = Object.values(answers);

            const response = await axios.post(`http://localhost:8080/submit-assignment`, {
                assignmentId: assignmentId,
                userName: localStorage.getItem("username"),
                answers: studentAnswers,
            });

            setGrade(response.data.grade);
        } catch (err) {
            console.error("Error submitting assignment:", err);
            alert("Failed to submit the assignment.");
        }
    };

    if (loading) return <p>Loading assignment...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div>
            <h1>{assignment.assignmentName}</h1>
            {grade !== null ? (
                <div>
                    <h2>Your Grade: {grade}</h2>
                    <button onClick={() => navigate(-1)}>Back</button>
                </div>
            ) : (
                <div>
                    {questions.map((question, index) => (
                        <div key={index} style={{ marginBottom: "20px" }}>
                            <h3>{question.question}</h3>
                            {question.options.map((option, optionIndex) => (
                                <div key={optionIndex}>
                                    <label>
                                        <input
                                            type="radio"
                                            name={`question-${index}`}
                                            value={option}
                                            onChange={() => handleAnswerChange(index, option)}
                                            checked={answers[index] === option}
                                        />
                                        {option}
                                    </label>
                                </div>
                            ))}
                        </div>
                    ))}
                    <button
                        onClick={handleSubmit}
                        disabled={Object.keys(answers).length !== questions.length}
                    >
                        Submit Answers
                    </button>
                </div>
            )}
        </div>
    );
};

export default TakeAssignment;



