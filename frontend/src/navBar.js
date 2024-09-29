import React from 'react';
import './App.css';
import logo from './logo.svg';
import { useNavigate } from 'react-router-dom'; // Import useNavigate

function NavBar() {
    const navigate = useNavigate(); // Initialize the navigate function

    const goToHomePage = () => {
        navigate('/'); // Navigate to the home page
    };

    const goToNewPage = () => {
        navigate('/login'); // Navigate to the new page
    };

    return (
        <div className="nav-bar">
            <div className="nav-logo" onClick={goToHomePage} style={{ cursor: 'pointer' }}>
                <img src={logo} alt="Logo" />
            </div>
            <div className="navBar-title" onClick={goToHomePage} style={{ cursor: 'pointer' }}>
                Learning Square
            </div>
            <div className="nav-icon">
                <button className="nav-icon-color" onClick={goToNewPage}>👤</button>
            </div>
        </div>
    );
}

export default NavBar;
