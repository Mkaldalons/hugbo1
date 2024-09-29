import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

function LoginPage() {
    const location = useLocation();
    const [isLogin, setIsLogin] = useState(true);
    const [username, setUsername] = useState('');
    const [name, setName] = useState('');          // Only for sign-up
    const [email, setEmail] = useState('');        // Only for sign-up
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');  // Only for sign-up
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        // Check the current path and update the form state accordingly
        setIsLogin(location.pathname === '/login');
    }, [location.pathname]);

    const toggleForm = () => {
        const newPath = isLogin ? '/signup' : '/login';
        navigate(newPath);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Check if passwords match during sign-up
        if (!isLogin && password !== confirmPassword) {
            setMessage('Passwords do not match');
            return;
        }

        const url = isLogin ? 'http://localhost:8080/login' : 'http://localhost:8080/signup';  // Corrected URLs
        const requestData = isLogin
            ? { username, password }  // Login requires only username and password
            : { username, name, email, password, confirmPassword };  // Sign-up requires more fields

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (response.ok) {
                const successMessage = await response.text();  // Get response message from server
                setMessage(successMessage);  // Display success message (e.g., "Login successful")

                // Optionally, delay the navigation to give the user time to see the message
                setTimeout(() => {
                    navigate('/');
                }, 2000);  // Redirect after 2 seconds
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
                <h1>{isLogin ? 'Login' : 'Sign Up'}</h1>
                <form className="auth-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Username:</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    {!isLogin && (  // These fields are only shown during sign-up
                        <>
                            <div className="form-group">
                                <label>Name:</label>
                                <input
                                    type="text"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Email:</label>
                                <input
                                    type="email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    required
                                />
                            </div>
                        </>
                    )}
                    <div className="form-group">
                        <label>Password:</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    {!isLogin && (
                        <div className="form-group">
                            <label>Confirm Password:</label>
                            <input
                                type="password"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                required
                            />
                        </div>
                    )}
                    <button type="submit" className="auth-btn">
                        {isLogin ? 'Login' : 'Sign Up'}
                    </button>
                </form>
                <button onClick={toggleForm} className="toggle-form-btn">
                    {isLogin ? 'Sign Up' : 'Login'}
                </button>
                {message && <p>{message}</p>}
            </div>
        </div>
    );
}

export default LoginPage;
