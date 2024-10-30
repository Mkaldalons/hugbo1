import React, { useEffect, useState } from "react";
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const EditAssignment = () => {
    const { assignmentId } = useParams();
    const navigate = useNavigate();
    const [assignment, setAssignment] = useState({
        assignmentName: '',
        dueDate: '',
        questionRequests: [] // Initialize with an empty array
    });

    useEffect(() => {
        const fetchAssignment = async () => {
            try {
                console.log("Id: ", assignmentId)
                const response = await axios.get(`http://localhost:8080/assignment/${assignmentId}`);
                const assignmentData = response.data;

                const questionRequests = JSON.parse(assignmentData.jsonData);

                setAssignment({
                    ...assignmentData,
                    questionRequests: questionRequests
                });
            } catch (error) {
                console.error('Error fetching assignment:', error);
            }
        };

        fetchAssignment();
    }, [assignmentId]);

    const handleQuestionChange = (index, field, value) => {
        const updatedQuestions = [...assignment.questionRequests];
        updatedQuestions[index] = { ...updatedQuestions[index], [field]: value };
        setAssignment({ ...assignment, questionRequests: updatedQuestions });
    };

    const handleOptionChange = (questionIndex, optionIndex, value) => {
        const updatedQuestions = [...assignment.questionRequests];
        updatedQuestions[questionIndex].options[optionIndex] = value;
        setAssignment({ ...assignment, questionRequests: updatedQuestions });
    };

    const handleAddOption = (questionIndex) => {
        const updatedQuestions = [...assignment.questionRequests];
        updatedQuestions[questionIndex].options.push("");
        setAssignment({ ...assignment, questionRequests: updatedQuestions });
    };

    const handleAddQuestion = () => {
        const newQuestion = {
            question: '',
            options: ["", "", "", ""],
            correctAnswer: ''
        };
        setAssignment({ ...assignment, questionRequests: [...assignment.questionRequests, newQuestion]});
    };

    const handleDueDateChange = (e) => {
        setAssignment({ ...assignment, dueDate: e.target.value });
    };

    const handleAssignmentNameChange = (e) => {
        setAssignment({ ...assignment, assignmentName: e.target.value});
    };

    const handleSave = async () => {
        try {
            const response = await axios.post('http://localhost:8080/edit', {
                assignmentId: assignmentId,
                dueDate: assignment.dueDate,
                assignmentName: assignment.assignmentName,
                questionRequests: assignment.questionRequests,
                courseId: assignment.courseId
            });

            if (response.data.message === 'Assignment updated') {
                navigate('/instructor');
            } else {
                console.log('Error updating assignment:', response.data.message);
            }
        } catch (error) {
            console.error('Error saving assignment:', error);
        }
    };

    if (!assignment || !assignment.questionRequests) {
        return <p>Loading...</p>; // Wait for assignment data to load
    }

    return (
        <div>
            <h2>Edit Assignment</h2>
            <label>Assignment Name:</label>
            <input
                type="text"
                value={assignment.assignmentName}
                onChange={handleAssignmentNameChange}
            />

            <label>Due Date:</label>
            <input
                type="date"
                value={assignment.dueDate}
                onChange={handleDueDateChange}
            />

            <h3>Questions</h3>
            {assignment.questionRequests.map((question, questionIndex) => (
                <div key={questionIndex}>
                    <label>Question:</label>
                    <input
                        type="text"
                        value={question.question || ''}
                        onChange={(e) => handleQuestionChange(questionIndex, "question", e.target.value)}
                    />

                    <label>Options:</label>
                    {question.options.map((option, optionIndex) => (
                        <div key={optionIndex}>
                            <input
                                type="text"
                                value={option || ''}
                                onChange={(e) => handleOptionChange(questionIndex, optionIndex, e.target.value)}
                            />
                        </div>
                    ))}

                    <button onClick={() => handleAddOption(questionIndex)}>Add Option</button>

                    <label>Correct Answer:</label>
                    <input
                        type="text"
                        value={question.correctAnswer || ''}
                        onChange={(e) => handleQuestionChange(questionIndex, "correctAnswer", e.target.value)}
                    />
                </div>
            ))}

            <button onClick={handleAddQuestion}>Add New Question</button>
            <button onClick={handleSave}>Save Changes</button>
        </div>
    );
};

export default EditAssignment;