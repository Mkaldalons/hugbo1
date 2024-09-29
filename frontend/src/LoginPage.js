import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

function LoginPage() {
    const location = useLocation();
    const [isLogin, setIsLogin] = useState(true);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
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
        const url = isLogin ? '/api/login' : '/api/signup';
        const requestData = isLogin
            ? { email, password }
            : { email, password, confirmPassword };

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (response.ok) {
                navigate('/'); // Redirect to home page on success
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
                        <label>Email:</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
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
