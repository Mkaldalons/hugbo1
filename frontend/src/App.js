import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from './components/NavBar/NavBar';
import './styles/global.css';
import './styles/variables.css';
import frontpage from './assets/frontPage.png';
import LoginPage from './components/Auth/LoginPage';
import Instructor from "./components/InstructorPage/Instructor";  // Import the Dashboard

function App() {
    const [username, setUsername] = useState('');

    return (
        <Router>
            <div className="App">
                <NavBar username={username} setUsername={setUsername} />
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
                    {/* Add route for the instructor dashboard */}
                    <Route
                        path="/instructor"
                        element={<Instructor />}
                    />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
