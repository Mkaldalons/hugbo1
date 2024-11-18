INSERT INTO Users (user_name, name, email, password, is_instructor, profile_image_path, recovery_email) VALUES
                                                                                                            ('mariaros98', 'María Rós', 'mariaros98@email.com', 'lykilord1', 0, '', ''),
                                                                                                            ('kennari', 'Kennari', 'kennari@email.com', 'lykilord', 1, '', ''),
                                                                                                            ('poi', 'poi', 'poi@poi.is', 'Poi', 1, '', ''),
                                                                                                            ('student123', 'student123', 'student@student.is', 'student', 1, 'uploads/profile-images/1731536787830_duck.jpg', ''),
                                                                                                            ('ekkiKennari', 'Ekki kennari', 'ekkiKennari@email.com', 'lykilord', 0, '', ''),
                                                                                                            ('lena', 'Helena Stefansdottir', 'prufa@email.com', 'BlaBla', 0, '', ''),
                                                                                                            ('lena', 'helena stefansdottir', 'lena@email.com', 'BlaBla', 0, '', ''),
                                                                                                            ('helena123', 'helena', 'helena@helena.is', '1234', 0, '', ''),
                                                                                                            ('lenakennir', 'rúsína', 'lenakennir@kennir.is', 'uppi', 0, '', ''),
                                                                                                            ('lenakennir', 'rúsína', 'ublubl@kennir.is', 'uppi', 0, '', ''),
                                                                                                            ('elli', 'elias nokkvi', 'elias@prufa.no', 'mamma', 0, '', ''),
                                                                                                            ('helli', 'helena stef', 'helli@no.is', 'nammi', 1, '', '');

INSERT INTO Students (student_id, user_name, name) VALUES
                                                       (1, 'mariaros98', 'María Rós'),
                                                       (2, 'notInstructor', 'Not Instructor'),
                                                       (3, 'ekkiKennari', 'Ekki kennari'),
                                                       (4, 'lena', 'Helena Stefansdottir'),
                                                       (6, 'helena123', 'helena'),
                                                       (7, 'lenakennir', 'rúsína'),
                                                       (8, 'elli', 'elias nokkvi');

INSERT INTO Courses (course_id, course_name, instructor, description) VALUES
                                                                          ('Tölvunarfræði 1101', 'Tölvunarfræði 1', 'null isThisNull', 'missing'),
                                                                          ('Tölvunarfræði101', 'Tölvunarfræði', 'null isThisNull', 'missing'),
                                                                          ('Stærðfræðimynstur101', 'Stærðfræðimynstur', 'kennari isThisNull', 'missing'),
                                                                          ('Kennsla101', 'Kennsla', 'kennari isThisNull', 'missing'),
                                                                          ('Áfangi101', 'Áfangi', 'kennari isThisNull', 'missing'),
                                                                          ('Nýr Áfangi101', 'Nýr Áfangi', 'kennari', 'missing'),
                                                                          ('HelenuAfangi101', 'HelenuAfangi', 'helli', 'missing'),
                                                                          ('helena202101', 'helena202', 'helli', 'missing'),
                                                                          ('syna_afanga101', 'syna_afanga', 'student123', 'missing');

INSERT INTO Student_Courses (student_id, course_id) VALUES
                                                        (1, 'Tölvunarfræði 1101'),
                                                        (4, 'Nýr Áfangi101'),
                                                        (1, 'Nýr Áfangi101'),
                                                        (4, 'HelenuAfangi101'),
                                                        (1, 'HelenuAfangi101'),
                                                        (1, 'syna_afanga101');

INSERT INTO Assignments (assignment_id, due_date, json_data, course_id, assignment_name, published) VALUES
                                                                                                        (5, 1730332800000, '[{"question":"Hvað er besta forritunamálið?","options":["Java","Python","C++","JavaScript"],"correctAnswer":"C++"}]', 'Kennsla101', 'Verkefni 1', 0),
                                                                                                        (8, 1730332800000, '[{"question":"Hvað er versta forritunarmálið?","options":["Java","C++","Python","JavaScript"],"correctAnswer":"JavaScript"}]', 'Kennsla101', 'Verkefni 2', 1),
                                                                                                        (9, 1730851200000, '[{"question":"Hvert af eftirfarandi er integer?","options":["5.5","\\"4\\"","5/6","1"],"correctAnswer":"1"},{"question":"Hvert af eftirfarandi er villa?","options":["5+5","5.4+3","\\"5\\"+2","5//2"],"correctAnswer":"\\"5\\"+2"}]', 'Tölvunarfræði 1101', 'Verkefni 3', 0),
                                                                                                        (11, 1765497600000, '[{"question":"hvað heitir mamma mín","options":["lala","pó","dipsí","eva"],"correctAnswer":"eva"}]', 'HelenuAfangi101', 'Assignment', 0);

INSERT INTO Assignment_Submissions (submission_id, assignment_id, assignment_grade, student_id) VALUES
                                                                                                    (1, 5, 7.7, 1),
                                                                                                    (2, 5, 8.7, 2),
                                                                                                    (3, 5, 10.0, 3);

