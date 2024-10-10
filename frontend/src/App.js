import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from './components/NavBar/NavBar';
import './styles/global.css';
import './styles/variables.css';
import frontpage from './assets/frontPage.png';
import LoginPage from './components/Auth/LoginPage';
import Instructor from './components/InstructorPage/Instructor';
import Student from './components/StudentPage/StudentPage';

function App() {
    const [username, setUsername] = useState('');
    const [isDarkMode, setIsDarkMode] = useState(false);

    const toggleDarkMode = () => {
        setIsDarkMode((prevMode) => !prevMode);
    };

    return (
        <Router>
            <div className={`App ${isDarkMode ? 'dark-mode' : ''}`}>
                <NavBar username={username} setUsername={setUsername} />
                <button onClick={toggleDarkMode} className="dark-mode-toggle">
                    {isDarkMode ? 'Switch to Light Mode' : 'Switch to Dark Mode'}
                </button>
                <Routes>
                    <Route
                        path="/"
                        element={
                            <div className="App-header">
                                <img src={frontpage} alt="frontpage" />
                            </div>
                        }
                    />
                    <Route
                        path="/login"
                        element={<LoginPage setUsername={setUsername} />}
                    />
                    <Route
                        path="/signup"
                        element={<LoginPage setUsername={setUsername} />}
                    />
                    <Route
                        path="/instructor"
                        element={<Instructor />}
                    />
                    <Route
                        path="/student"
                        element={<Student />}
                    />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
