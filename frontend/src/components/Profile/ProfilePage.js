import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ProfilePage.css'; // Optional: Create a new CSS file for ProfilePage styling

const ProfilePage = () => {
    const navigate = useNavigate();
    const [profileImage, setProfileImage] = useState(null);
    const [previewImage, setPreviewImage] = useState(null);
    const [username, setUsername] = useState(localStorage.getItem('username') || '');
    const [name, setName] = useState('');
    const [recoveryEmail, setRecoveryEmail] = useState('');
    const [email, setEmail] = useState('');

    const placeholderImage = 'http://localhost:8080/uploads/profile-images/base_profile.jpg';

    useEffect(() => {
        const fetchUserInfo = async () => {
        try {
            const userInfoResponse = await axios.get('http://localhost:8080/get-user-info', {
                params: { username: localStorage.getItem('username') }
            });
            const userData = userInfoResponse.data;

            setEmail(userData.email);
            setRecoveryEmail(userData.recoveryEmail);
            setName(userData.name);

            if (userData.imagePath) {
                setProfileImage(`http://localhost:8080/${userData.imagePath}`);
            } else {
                setProfileImage(placeholderImage);
            }
        } catch (error) {
            console.error('Error fetching user info:', error);
        }
    };

        fetchUserInfo();
    }, [username]);

    // Redirect to the Update Password page
    const handleUpdatePassword = () => {
        navigate('/update-password');
    };

    // Function to handle account deletion
    const handleDeleteAccount = async () => {
        if (window.confirm('Are you sure you want to delete your account? This action is irreversible.')) {
            try {
                await axios.post('http://localhost:8080/delete-account');
                alert('Account deleted successfully.');
                navigate('/'); // Redirect to home or login page after deletion
            } catch (error) {
                console.error('Error deleting account:', error);
                alert('Failed to delete account.');
            }
        }
    };

    const handleUpdateEmail = () => {
        navigate('/update-recovery-email');
    }

     // Handle image selection and preview
     const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setProfileImage(file);
            setPreviewImage(URL.createObjectURL(file)); // Create preview URL
        }
    };

    // Save the profile image
    const handleSaveImage = async () => {
        if (!previewImage) {
            alert("Please select an image first.");
            return;
        }

        const formData = new FormData();
        formData.append('profileImage', profileImage);
        formData.append('username', username);

        try {
            const response = await axios.post('http://localhost:8080/upload-profile-image', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });

            if (response.data.imagePath) {
                setProfileImage(`http://localhost:8080/${response.data.imagePath}`);
                setPreviewImage(null); // Clear preview after upload
                alert("Profile image uploaded successfully!");
            }
        } catch (error) {
            console.error('Error uploading profile image:', error);
            alert('Failed to upload image.');
        }
    };


    return (
        <div className="profile-container">
            <h2>My Profile</h2>

            <div className="profile-info">
                <p><strong>Username:</strong> {username}</p>
                <p><strong>Name:</strong> {name}</p>
                <p><strong>Email:</strong> {email}</p>
                <p><strong>Recovery Email:</strong> {recoveryEmail}</p>
            </div>

            <div className="profile-content">
                {/* Profile Image Display on the Right */}
                <div className="profile-image-display">
                {profileImage ? (
                    <img src={profileImage} alt="Profile" className="profile-image" />
                ) : (
                    <img src={placeholderImage} alt="Placeholder" className="profile-image" />
                )}
            </div>

                <div className="profile-image-section">
                    <label htmlFor="profileImage" className="profile-image-label">
                        {previewImage ? (
                            <img src={previewImage} alt="Profile Preview" className="profile-image-preview" />
                        ) : (
                            <span>Select a profile image</span>
                        )}
                    </label>
                    <input
                        type="file"
                        id="profileImage"
                        accept="image/*"
                        onChange={handleImageChange}
                        style={{ display: 'none' }} // Hide the default file input
                    />
                    <button className="btn-save-image" onClick={handleSaveImage}>Save Image</button>
                </div>
            </div>

            <div className="profile-options">
                <button className="btn-update" onClick={handleUpdatePassword}>
                    Update Password
                </button>
                <button className="btn-delete" onClick={handleDeleteAccount}>
                    Delete Account
                </button>
                <button className="btn-update" onClick={handleUpdateEmail}>
                    Update Recovery Email
                </button>
            </div>
        </div>
    );
};

export default ProfilePage;
