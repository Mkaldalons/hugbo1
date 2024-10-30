import React, { useEffect, useState } from 'react';
import axios from 'axios';

const AverageGrade = ({ assignmentId }) => {
    const [averageGrade, setAverageGrade] = useState(null);

    useEffect(() => {
        const fetchAverageGrade = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/average-grade/${assignmentId}`);
                setAverageGrade(response.data);
            } catch (error) {
                console.error(`Error fetching average grade for assignment ${assignmentId}:`, error);
            }
        };

        fetchAverageGrade();
    }, [assignmentId]);

    return (
        <span>
            {(averageGrade !== null && averageGrade !== 0) ? `Average Grade: ${averageGrade.toFixed(2)}` : 'No submissions yet'}
        </span>
    );
};

export default AverageGrade;
