import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from "./navBar";
import './App.css';
import frontpage from "./frontPage.png";
import LoginPage from './LoginPage'; // Import the LoginPage component

function App() {
    return (
        <Router>
            <div className="App">
                <NavBar />
                <Routes>
                    <Route path="/" element={
                        <div className="App-header">
                            <img src={frontpage} alt="frontpage" />
                        </div>
                    } />
                    <Route path="/login" element={<LoginPage />} /> {/* Correct path for login */}
                    <Route path="/signup" element={<LoginPage />} /> {/* Correct path for signup */}
                </Routes>
            </div>
        </Router>
    );
}

export default App;
