import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import LoginForm from './LoginForm';
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
            ? { username, password, isInstructor}
            : { username, name, email, password, confirmPassword, isInstructor };

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            const responseData = await response.json();

            if (response.ok) {
                setMessage(responseData.message);

                setUsername(username);
                localStorage.setItem('username', username)

                setTimeout(() => {
                    if (responseData.isInstructor) {
                        navigate('/instructor');
                    } else {
                        navigate('/student');
                    }
                }, 2000);
            } else {
                const error = await response.text();
                setMessage(`Error: ${error}`);
            }
        } catch (error) {
            setMessage(`Error: ${error.message}`);
        }
    };

    const handlePasswordUpdateClick = () => {
        navigate('/update-password');
    };

    return (
        <div className="login-page-container">
            <div className="auth-container">
                <h1>{isLogin ? 'Login' : 'Sign Up'}</h1>
                <LoginForm
                    isLogin={isLogin}
                    username={username}
                    setUsernameInput={setUsernameInput}
                    name={name}
                    setName={setName}
                    email={email}
                    setEmail={setEmail}
                    password={password}
                    setPassword={setPassword}
                    confirmPassword={confirmPassword}
                    setConfirmPassword={setConfirmPassword}
                    isInstructor={isInstructor}
                    setIsInstructor={setIsInstructor}
                    handleSubmit={handleSubmit}
                />
                <button onClick={toggleForm} className="toggle-form-btn">
                    {isLogin ? 'Sign Up' : 'Login'}
                </button>
                <button
                    onClick={handlePasswordUpdateClick}
                    className="toggle-form-btn"
                >
                    Update Password
                </button>
                {message && <p>{message}</p>}
            </div>
        </div>
    );
}

export default LoginPage;