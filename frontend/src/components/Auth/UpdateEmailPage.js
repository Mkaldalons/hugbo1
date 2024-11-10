import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './AuthForm.css';
import './PageContainer.css';

function UpdateEmailPage({ username }) {

    const [newEmail, setNewEmail] = useState('');
    const [confirmEmail, setConfirmEmail] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleUpdateEmail = async (event) => {
        event.preventDefault();

        if (newEmail !== confirmEmail) {
            setMessage('Emails do not match');
            return;
        }

        try {
            const url = 'http://localhost:8080/update-recovery-email';
            const requestData = {
                username,
                recoveryEmail: newEmail,
            };

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (response.ok) {
                const result = await response.json();
                setMessage(result.status);
                setNewEmail('');
                setConfirmEmail('');
                setTimeout(() => {
                    navigate('/profile');
                }, 2000);
            } else {
                const error = await response.json();
                setMessage(error.status);
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <div className="login-page-container">
            <div className="auth-container">
                <h1>Add or Update Recovery Email</h1>
                <form className="auth-form" onSubmit={handleUpdateEmail}>
                    <div className="form-group">
                        <label>Recovery Email:</label>
                        <input
                            type="email"
                            value={newEmail}
                            onChange={(e) => setNewEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>Confirm Recovery Email:</label>
                        <input
                            type="email"
                            value={confirmEmail}
                            onChange={(e) => setConfirmEmail(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="auth-btn">
                        Save Recovery Email
                    </button>
                </form>
                {message && <p>{message}</p>}
                <button
                    onClick={() => navigate('/login')}
                    className="toggle-form-btn"
                >
                    Login
                </button>
            </div>
        </div>
    );
}

export default UpdateEmailPage;
