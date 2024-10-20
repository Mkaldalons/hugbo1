import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './AuthForm.css';
import './PageContainer.css';

function UpdatePasswordPage({ username }) {
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handlePasswordUpdate = async (event) => {
        event.preventDefault();

        if (newPassword !== confirmPassword) {
            setMessage('New passwords do not match');
            return;
        }

        try {
            // Fetch user details by username
            const userDetailsResponse = await fetch(`http://localhost:8080/users/${username}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!userDetailsResponse.ok) {
                setMessage('Error fetching user details');
                return;
            }

            const userDetails = await userDetailsResponse.json();

            // Check if the old password matches the one in the database
            if (userDetails.password !== oldPassword) {
                setMessage('Old password is incorrect');
                return;
            }

            // Proceed to update the password
            const url = 'http://localhost:8080/update-password';
            const requestData = {
                username,
                oldPassword,
                newPassword,
            };

            const response = await fetch(url, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (response.ok) {
                const successMessage = await response.text();
                setMessage(successMessage);
                setOldPassword('');
                setNewPassword('');
                setConfirmPassword('');
                setTimeout(() => {
                    navigate('/login');
                }, 2000);
            } else {
                const error = await response.text();
                setMessage(`Error: ${error}`);
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    return (
        <div className="login-page-container">
            <div className="auth-container">
                <h1>Update Password</h1>
                <form className="auth-form" onSubmit={handlePasswordUpdate}>
                    <div className="form-group">
                        <label>Old Password:</label>
                        <input
                            type="password"
                            value={oldPassword}
                            onChange={(e) => setOldPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>New Password:</label>
                        <input
                            type="password"
                            value={newPassword}
                            onChange={(e) => setNewPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>Confirm New Password:</label>
                        <input
                            type="password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="auth-btn">
                        Update Password
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

export default UpdatePasswordPage;
