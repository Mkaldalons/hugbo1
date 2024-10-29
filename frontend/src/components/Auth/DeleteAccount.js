import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './AuthForm.css';
import './PageContainer.css';

function DeleteAccount({ username }) {
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleAccountDeletion = async (event) => {
        event.preventDefault();
        console.log("Starting account deletion request");

        try {
            const url = 'http://localhost:8080/delete-account';
            const requestData = { username };

            console.log("Request data:", requestData);

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            console.log("Response received:", response);

            if (response.ok) {
                const result = await response.json();
                setMessage('Account deleted successfully.');
                setTimeout(() => {
                    navigate('/login');
                }, 2000);
            } else {
                const error = await response.json();
                console.log("Error response:", error);
                setMessage(error.status || 'Failed to delete account.');
            }
        } catch (error) {
            console.error("Fetch error:", error);
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <div className="login-page-container">
            <div className="auth-container">
                <h1>Delete Account</h1>
                <form className="auth-form" onSubmit={handleAccountDeletion}>
                    <button type="submit" className="auth-btn">
                        Delete Account
                    </button>
                </form>
                {message && <p>{message}</p>}
                <button
                    onClick={() => navigate('/login')}
                    className="toggle-form-btn"
                >
                    Back to Login
                </button>
            </div>
        </div>
    );
}

export default DeleteAccount;