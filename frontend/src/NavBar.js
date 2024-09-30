import React, { useState } from 'react';
import './App.css';
import logo from './logo.svg';
import { useNavigate } from 'react-router-dom';

function NavBar({ username, setUsername }) {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false); // State to toggle dropdown
    const navigate = useNavigate();

    const goToHomePage = () => {
        navigate('/');
    };

    const goToLoginPage = () => {
        navigate('/login');
    };

    const handleIconClick = () => {
        // If the user is logged in, show the dropdown for logout, otherwise navigate to login
        if (username) {
            setIsDropdownOpen(!isDropdownOpen); // Toggle dropdown visibility
        } else {
            goToLoginPage(); // Redirect to login page if not logged in
        }
    };

    const handleLogout = () => {
        setUsername(''); // Clear username to simulate logout
        setIsDropdownOpen(false); // Close the dropdown
        navigate('/'); // Redirect to home page after logging out
    };

    return (
        <div className="nav-bar">
            <div className="nav-logo" onClick={goToHomePage} style={{ cursor: 'pointer' }}>
                <img src={logo} alt="Logo" />
            </div>
            <div className="navBar-title" onClick={goToHomePage} style={{ cursor: 'pointer' }}>
                Learning Square
            </div>
            <div className="nav-user-info">
                {/* Display the username if logged in */}
                {username && <span className="nav-username">Hello, {username}</span>}
                <button className="nav-icon-color" onClick={handleIconClick}>👤</button>

                {/* Dropdown Menu for logout */}
                {isDropdownOpen && username && (
                    <div className="dropdown-menu">
                        <div className="dropdown-item" onClick={handleLogout}>
                            Log out
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default NavBar;
