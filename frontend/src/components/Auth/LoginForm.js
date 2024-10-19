import React from 'react';

function LoginForm({
                       isLogin,
                       username,
                       setUsernameInput,
                       name,
                       setName,
                       email,
                       setEmail,
                       password,
                       setPassword,
                       confirmPassword,
                       setConfirmPassword,
                       isInstructor,
                       setIsInstructor,
                       handleSubmit,
                   }) {
    return (
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
            {!isLogin && (
                <div className="form-group">
                    <label>Instructor:</label>
                    <input
                        type="checkbox"
                        checked={isInstructor}
                        onChange={(e) => setIsInstructor(e.target.checked)}
                    />
                </div>
            )}
            <button type="submit" className="auth-btn">
                {isLogin ? 'Login' : 'Sign Up'}
            </button>
        </form>
    );
}

export default LoginForm;