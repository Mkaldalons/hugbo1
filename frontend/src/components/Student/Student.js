import React, { useState, useEffect } from "react";

const Student = () => {
    const [users, setUsers] = useState([]); // List of users
    const [courses, setCourses] = useState([]); // List of courses
    const [searchTerm, setSearchTerm] = useState(""); // Search input for users
    const [selectedUser, setSelectedUser] = useState(null); // Selected user from search
    const [selectedCourse, setSelectedCourse] = useState(""); // Selected course from dropdown
    const [pairedData, setPairedData] = useState([]); // User-course pairs

    // Fetch users and courses when the component loads
    useEffect(() => {
        // Mock fetch users and courses
        const fetchUsers = async () => {
            // Replace with your actual API call
            setUsers(["Andri", "Lenovo", "John Doe", "Jane Doe"]);
        };
        const fetchCourses = async () => {
            // Replace with your actual API call
            setCourses(["Math 101", "History 202", "Science 303"]);
        };
        fetchUsers();
        fetchCourses();
    }, []);

    // Handler for adding a user to a course
    const handleAddUserToCourse = () => {
        if (selectedUser && selectedCourse) {
            // Add user-course pair to the list
            const newPair = { user: selectedUser, course: selectedCourse };
            setPairedData([...pairedData, newPair]);
            // Reset selections
            setSelectedUser(null);
            setSearchTerm("");
            setSelectedCourse("");
        }
    };

    // Filter users based on search term
    const filteredUsers = users.filter(user =>
        user.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div style={{ padding: "20px" }}>
            <h1>User Course Manager</h1>

            {/* Search Users */}
            <div>
                <input
                    type="text"
                    placeholder="Search users..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
                <ul>
                    {filteredUsers.map((user, index) => (
                        <li
                            key={index}
                            onClick={() => setSelectedUser(user)}
                            style={{
                                cursor: "pointer",
                                backgroundColor: selectedUser === user ? "#d3d3d3" : "transparent",
                            }}
                        >
                            {user}
                        </li>
                    ))}
                </ul>
            </div>

            {/* Dropdown for selecting courses */}
            <div>
                <label htmlFor="course-select">Select a course:</label>
                <select
                    id="course-select"
                    value={selectedCourse}
                    onChange={(e) => setSelectedCourse(e.target.value)}
                >
                    <option value="">-- Select a Course --</option>
                    {courses.map((course, index) => (
                        <option key={index} value={course}>
                            {course}
                        </option>
                    ))}
                </select>
            </div>

            {/* Button to add user to the selected course */}
            <button onClick={handleAddUserToCourse} disabled={!selectedUser || !selectedCourse}>
                Add {selectedUser} to {selectedCourse}
            </button>

            {/* Paired users and courses */}
            <div>
                <h2>Paired Users and Courses</h2>
                <textarea
                    readOnly
                    value={pairedData
                        .map((pair) => `${pair.user} is enrolled in ${pair.course}`)
                        .join("\n")}
                    rows="10"
                    cols="50"
                />
            </div>
        </div>
    );
};

export default Student;
