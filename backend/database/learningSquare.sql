CREATE TABLE Users (
                       user_name TEXT,
                       name TEXT,
                       email TEXT PRIMARY KEY,
                       password TEXT,
                       is_instructor BOOLEAN,
                       profile_image_path TEXT,
                       recovery_email TEXT
);

CREATE TABLE Students (
                          student_id INTEGER PRIMARY KEY AUTOINCREMENT,
                          user_name TEXT,
                          name TEXT,
                          FOREIGN KEY (user_name, name) REFERENCES Users(user_name, name)
);

CREATE TABLE Courses (
                         course_id VARCHAR PRIMARY KEY,
                         course_name TEXT,
                         instructor TEXT,
                         description TEXT
);

CREATE TABLE Student_Courses (
                                 student_id INTEGER NOT NULL,
                                 course_id VARCHAR NOT NULL,
                                 PRIMARY KEY (student_id, course_id),
                                 FOREIGN KEY (student_id) REFERENCES Students(student_id),
                                 FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

CREATE TABLE Assignments (
                             assignment_id INTEGER PRIMARY KEY AUTOINCREMENT,
                             due_date DATE,
                             json_data TEXT,
                             course_id VARCHAR,
                             assignment_name TEXT,
                             published BOOLEAN DEFAULT FALSE,
                             FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

CREATE TABLE Assignment_Submissions (
                            submission_id INTEGER PRIMARY KEY AUTOINCREMENT,
                            assignment_id INTEGER,
                            assignment_grade DOUBLE(3, 2),
                            student_id INTEGER,
                            FOREIGN KEY (assignment_id) REFERENCES Assignments(assignment_id),
                            FOREIGN KEY (student_id) REFERENCES Students(student_id)
);
