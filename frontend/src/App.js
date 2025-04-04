import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NavBar from "./components/NavBar/NavBar";
import "./styles/global.css";
import "./styles/variables.css";
import frontpage from "./assets/frontPage.png";
import LoginPage from "./components/Auth/LoginPage";
import Instructor from "./components/InstructorPage/Instructor";
import Student from "./components/StudentPage/StudentPage";
import UpdatePasswordPage from "./components/Auth/UpdatePasswordPage";
import UpdateEmailPage from "./components/Auth/UpdateEmailPage";
import DeleteAccount from "./components/Auth/DeleteAccount";
import Assignment from "./components/Assignment/Assignment";
import Course from "./components/Course/Course";
import StudentAssignments from "./components/StudentsAssignment/StudentAssignment";
import StudentView from "./components/StudentView/StudentView";
import StudentGrades from "./components/StudentGrades/StudentGrades";
import EditAssignment from "./components/Assignment/EditAssignment";
import ViewAssignment from "./components/Assignment/ViewAssignment";
import MyCoursesStudent from "./components/StudentView/MyCoursesStudent";
import ViewCourseAssignments from "./components/StudentView/ViewCourseAssignments";
import TakeAssignment from "./components/StudentView/TakeAssignment";

import CourseView from "./components/Course/CourseView"
import ProfilePage from './components/Profile/ProfilePage';
import CourseDetailsView from "./components/Course/CourseDetailsView";

function App() {
  const [username, setUsername] = useState("");

  return (
    <Router>
      <div className="App">
        <NavBar username={username} setUsername={setUsername} />
        <Routes>
          <Route
            path="/"
            element={
              <div className="App-header">
                {" "}
                <img src={frontpage} alt="frontpage" />
              </div>
            }
          />
          <Route
            path="/login"
            element={<LoginPage setUsername={setUsername} />}
          />
          <Route
            path="/signup"
            element={<LoginPage setUsername={setUsername} />}
          />
          <Route
            path="/update-password"
            element={<UpdatePasswordPage username={username} />}
          />
          <Route
            path="/update-recovery-email"
            element={<UpdateEmailPage username={username} />}
          />
          <Route
            path="/delete-account"
            element={<DeleteAccount username={username} />}
          />
          <Route path="/instructor" element={<Instructor />} />
          <Route path="/student" element={<Student />} />
          <Route path="/assignment" element={<Assignment />} />
          <Route path="/view-assignment/:assignmentId" element={< ViewAssignment />} />
          <Route
            path="/edit-assignment/:assignmentId"
            element={<EditAssignment />}
          />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/course" element={<Course />} />
          <Route path="/courseView" element={<CourseView />} />
          <Route path="/courseView/:courseId" element={<CourseDetailsView />} />
          <Route path="/studentAssignments" element={<StudentAssignments />} />
          <Route path="/studentView" element={<StudentView />} />
          <Route path="/my-courses-student/:userName" element={<MyCoursesStudent />} />
          <Route path="/view-course-assignments/:courseId" element={<ViewCourseAssignments/>}/>
          <Route path="/take-assignment/:assignmentId" element={<TakeAssignment/>}/>
          <Route path="/studentGrades" element={<StudentGrades />} />
        </Routes>
      </div>
    </Router>
  );

}

export default App;