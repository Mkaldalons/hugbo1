import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './AuthForm.css';
import './PageContainer.css';

function LoginPage({ setUsername }) {
    const location = useLocation();
    const [isLogin, setIsLogin] = useState(true);
    const [username, setUsernameInput] = useState('');
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [message, setMessage] = useState('');
    const [isInstructor, setIsInstructor] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        setIsLogin(location.pathname === '/login');
    }, [location.pathname]);

    const toggleForm = () => {
        setMessage('');
        const newPath = isLogin ? '/signup' : '/login';
        navigate(newPath);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!isLogin && password !== confirmPassword) {
            setMessage('Passwords do not match');
            return;
        }

        const url = isLogin ? 'http://localhost:8080/login' : 'http://localhost:8080/signup';
        const requestData = isLogin
            ? { username, password }
            : { username, name, email, password, confirmPassword, isInstructor };

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (response.ok) {
                const successMessage = await response.text();
                setMessage(successMessage);

                setUsername(username);

                setTimeout(() => {
                    navigate('/instructor');
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
                <h1>{isLogin ? 'Login' : 'Sign Up'}</h1>
                <form className="auth-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Username:</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsernameInput(e.target.value)}
                            required
                        />
                    </div>
                    {!isLogin && (
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
                        <>
                            <div className="form-group">
                                <label>Confirm Password:</label>
                                <input
                                    type="password"
                                    value={confirmPassword}
                                    onChange={(e) => setConfirmPassword(e.target.value)}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label>Instructor:</label>
                                <input
                                    type="checkbox"
                                    checked={isInstructor}
                                    onChange={(e) => setIsInstructor(e.target.checked)}
                                    className="checkbox-input"
                                />
                            </div>
                        </>
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
