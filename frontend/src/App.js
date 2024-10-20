import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from './components/NavBar/NavBar';
import './styles/global.css';
import './styles/variables.css';
import frontpage from './assets/frontPage.png';
import LoginPage from './components/Auth/LoginPage';
import Instructor from './components/InstructorPage/Instructor';
import Student from './components/StudentPage/StudentPage';
import UpdatePasswordPage from './components/Auth/UpdatePasswordPage';
import Assignment from './components/Assignment/Assignment';
import Course from './components/Course/Course';
import StudentAssignments from "./components/StudentsAssignment/StudentAssignment";
import StudentView from "./components/StudentView/StudentView";
import StudentGrades from "./components/StudentGrades/StudentGrades";

function App() {
    const [username, setUsername] = useState('');

    return (
        <Router>
            <div className="App">
                <NavBar username={username} setUsername={setUsername} />
                <Routes>
                    <Route path="/" element={ <div className="App-header"> <img src={frontpage} alt="frontpage" /></div>}/>
                    <Route path="/login" element={<LoginPage setUsername={setUsername} />} />
                    <Route path="/signup" element={<LoginPage setUsername={setUsername} />} />
                    <Route path="/update-password" element={<UpdatePasswordPage username={username} />} />
                    <Route path="/instructor" element={<Instructor />} />
                    <Route path="/student" element={<Student />} />
                    <Route path="/assignment" element={<Assignment />} />
                    <Route path="/course" element={<Course />} />
                    <Route path="/studentAssignments" element={<StudentAssignments />} />
                    <Route path="/studentView" element={<StudentView />} />
                    <Route path="/studentGrades" element={<StudentGrades />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;

