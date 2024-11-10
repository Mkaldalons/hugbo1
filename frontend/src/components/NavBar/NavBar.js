import React, { useState, useEffect } from 'react';
import './NavBar.css';
import './Dropdown.css';
import logo from '../../assets/logo.svg';
import { useNavigate } from 'react-router-dom';

function NavBar({ username, setUsername }) {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [isInstructor, setIsInstructor] = useState(null); // To store user role
    const navigate = useNavigate();

    useEffect(() => {
        if (username) {
            fetch(`/users/${username}`)
                .then(response => response.json())
                .then(data => setIsInstructor(data.isInstructor))
                .catch(error => console.error('Error fetching user role:', error));
        }
    }, [username]);

    const goToHomePage = () => {
        if (isInstructor === null) {
            // If the user role hasn't been fetched yet, prevent navigation
            return;
        }
        if (isInstructor) {
            navigate('/instructor');  // Navigate to instructor page
        } else {
            navigate('/student');     // Navigate to student page
        }
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

    const logout = () => {
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
                        <div className="dropdown-item" onClick={() => navigate('/profile')}>
                            My Profile
                        </div>
                        <div className="dropdown-item" onClick={logout}>
                            Log out
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default NavBar;
