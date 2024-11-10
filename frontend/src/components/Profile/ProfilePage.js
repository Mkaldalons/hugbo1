import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ProfilePage.css'; // Optional: Create a new CSS file for ProfilePage styling

const ProfilePage = () => {
    const navigate = useNavigate();
    const [profileImage, setProfileImage] = useState(null);
    const [previewImage, setPreviewImage] = useState(null);
    const [username, setUsername] = useState(localStorage.getItem('username') || ''); // Retrieve username from local storage or set it as empty

    const placeholderImage = 'http://localhost:8080/uploads/profile-images/base_profile.jpg';

    useEffect(() => {
        const fetchProfileImage = async () => {
            if (!username) return; // Ensure username is present before fetching
    
            try {
                const response = await axios.get('http://localhost:8080/get-profile-image', {
                    params: { username },
                });
    
                // Check if an image path is returned and set it, or use a default placeholder
                if (response.data.imagePath) {
                    setProfileImage(`http://localhost:8080/${response.data.imagePath}`);
                } else {
                    setProfileImage('/path/to/default-placeholder.png'); // Use a placeholder if no image is available
                }
            } catch (error) {
                console.error('Error fetching profile image:', error);
                setProfileImage(placeholderImage); // Optional: set a placeholder on error
            }
        };
    
        fetchProfileImage();
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
