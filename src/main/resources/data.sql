INSERT INTO subject(id, descrizione, name)
VALUES (nextval('subject_seq'), 'Matematica', 'Matematica'),
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


INSERT INTO public.credentials (id, student_id, teacher_id, password, role, username, oauth_user, registration_complete)
VALUES (nextval('credentials_seq'), null, 1, '$2a$10$w/I292S9GLPxJdWQDXltmulmw01uWCV7R6VFlj6MMlyGeSbeCpeCq', 'TEACHER',
        'teacher', false, true),
       (nextval('credentials_seq'), 51, null, '$2a$10$w/I292S9GLPxJdWQDXltmulmw01uWCV7R6VFlj6MMlyGeSbeCpeCq', 'STUDENT',
        'student', false, true);



INSERT INTO public.listing (hourly_rate, id, subject_id, teacher_id, description, title)
VALUES (15, 2, 51, 1, 'Lezioni di fisica per liceo e università.', 'Fisica con metodo'),
       (12, 3, 101, 1, 'Ripetizioni di chimica organica e inorganica.', 'Chimica facile'),
       (14, 4, 151, 1, 'Lezioni di biologia per superiori e test d''ingresso.', 'Biologia per tutti'),
       (18, 5, 201, 1, 'Programmazione base e avanzata.', 'Coding 101'),
       (11, 6, 251, 1, 'Storia moderna e contemporanea.', 'Viaggio nella storia'),
       (13, 7, 301, 1, 'Geografia politica ed economica.', 'Capire il mondo'),
       (17, 8, 351, 1, 'Filosofia antica, moderna e contemporanea.', 'Pensiero critico'),
       (16, 9, 401, 1, 'Letteratura italiana con focus su Dante e Manzoni.', 'Classici Italiani'),
       (15, 10, 451, 1, 'Conversazione e grammatica inglese.', 'English for Life');


INSERT INTO public.availability (date, end_time, start_time, id, listing_id)
VALUES ('2025-06-20', '11:00:00', '09:00:00', 2, 2),
       ('2025-06-21', '15:00:00', '13:00:00', 3, 3),
       ('2025-06-22', '17:00:00', '15:00:00', 4, 4),
       ('2025-06-23', '19:00:00', '17:00:00', 5, 5),
       ('2025-06-24', '10:00:00', '08:00:00', 6, 6),
       ('2025-06-25', '12:00:00', '10:00:00', 7, 7),
       ('2025-06-26', '16:00:00', '14:00:00', 8, 8),
       ('2025-06-27', '18:00:00', '16:00:00', 9, 9),
       ('2025-06-28', '20:00:00', '18:00:00', 10, 10);
