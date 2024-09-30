import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from './NavBar';
import './App.css';
import frontpage from './frontPage.png';
import LoginPage from './LoginPage';

function App() {
    const [username, setUsername] = useState(''); // State to store the username

    return (
        <Router>
            <div className="App">
                <NavBar username={username} setUsername={setUsername} /> {/* Pass setUsername */}
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
                        element={<LoginPage setUsername={setUsername} />} // Pass the setUsername function as a prop
                    />
                    <Route path="/signup" element={<LoginPage setUsername={setUsername} />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
