import React, { useState } from 'react';
import './NavBar.css';
import './Dropdown.css';
import logo from '../../assets/logo.svg';
import { useNavigate } from 'react-router-dom';

function NavBar({ username, setUsername }) {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const navigate = useNavigate();

    const goToHomePage = () => {
        navigate('/');
    };

    const goToLoginPage = () => {
        navigate('/login');
    };

    const handleIconClick = () => {
        if (username) {
            setIsDropdownOpen(!isDropdownOpen);
        } else {
            goToLoginPage();
        }
    };

    const handleLogout = () => {
        setUsername('');
        setIsDropdownOpen(false);
        navigate('/');
    };

    return (
        <div className="nav-bar">
            <div className="nav-logo">
                <img src={logo} alt="Logo" />
            </div>
            <div className="navBar-title" onClick={goToHomePage} style={{ cursor: 'pointer' }}>
                Learning Square
            </div>
            <div className="nav-user-info">
                {username && <span className="nav-username">Hello, {username}</span>}
                <button className="nav-icon-color" onClick={handleIconClick}>👤</button>

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