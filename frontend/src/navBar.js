import React from 'react';
import './App.css'; // Importing the CSS file for styling
import logo from './logo.svg'; // Importing the logo

function NavBar() {
    const goToHomePage = () => {
        window.location.href = '.'; // Navigate to the home page
    };

    return (
        <div className="nav-bar">
            <div className="nav-logo">
                <img src={logo} alt="Logo" />
            </div>
            <div className="navBar-title" onClick={goToHomePage} style={{ cursor: 'pointer' }}>
                Learning Square
            </div>
            <div className="nav-icon">
                <button className="nav-icon-color">👤</button>
            </div>
        </div>
    );
}

export default NavBar;
