import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from "./navBar";
import './App.css';
import frontpage from "./frontPage.png";
import NewPage from './loginPage'; // Import the new page component

function App() {
    return (
        <Router>
            <div className="App">
                <NavBar />
                <Routes>
                    <Route path="/" element={
                        <div className="App-header">
                            <img src={frontpage} alt="frontpage"/>
                        </div>
                    } />
                    <Route path="/new-page" element={<NewPage />} /> {/* New Route */}
                </Routes>
            </div>
        </Router>
    );
}

export default App;
