INSERT INTO subject(id, descrizione, name) VALUES
                                               (nextval('subject_seq'), 'Matematica', 'Matematica'),
                                               (nextval('subject_seq'), 'Fisica', 'Fisica'),
                                               (nextval('subject_seq'), 'Chimica', 'Chimica'),
                                               (nextval('subject_seq'), 'Biologia', 'Biologia'),
                                               (nextval('subject_seq'), 'Informatica', 'Informatica'),
                                               (nextval('subject_seq'), 'Storia', 'Storia'),
                                               (nextval('subject_seq'), 'Geografia', 'Geografia'),
                                               (nextval('subject_seq'), 'Filosofia', 'Filosofia'),
                                               (nextval('subject_seq'), 'Letteratura Italiana', 'Italiano'),
                                               (nextval('subject_seq'), 'Lingua Inglese', 'Inglese'),
                                               (nextval('subject_seq'), 'Educazione Civica', 'Educazione Civica'),
                                               (nextval('subject_seq'), 'Educazione Fisica', 'Educazione Fisica'),
                                               (nextval('subject_seq'), 'Arte e Immagine', 'Arte'),
                                               (nextval('subject_seq'), 'Tecnologia', 'Tecnologia');

INSERT INTO public.users (id, user_type, email, first_name, last_name)
VALUES (nextval('users_seq'), 'TEACHER', 'teacher@mail.com', 'Teacher', 'Teacher'),
        (nextval('users_seq'), 'STUDENT', 'student@mail.com', 'Student', 'Student');


INSERT INTO public.credentials (id, student_id, teacher_id, password, role, username)
VALUES (nextval('credentials_seq'), null, 1, '$2a$10$w/I292S9GLPxJdWQDXltmulmw01uWCV7R6VFlj6MMlyGeSbeCpeCq', 'TEACHER', 'teacher'),
       (nextval('credentials_seq'), 51, null, '$2a$10$w/I292S9GLPxJdWQDXltmulmw01uWCV7R6VFlj6MMlyGeSbeCpeCq', 'STUDENT', 'student');
