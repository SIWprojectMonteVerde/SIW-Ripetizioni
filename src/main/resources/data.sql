
insert into subject(id,descrizione,name)
VALUES (nextval('subject_seq'),'Matematica','Matematica');
INSERT INTO public.users (id, user_type, email, first_name, last_name)
VALUES (nextval('users_seq'), 'TEACHER', 'teacher@mail.com', 'Teacher', 'Teacher');

INSERT INTO public.credentials (id, student_id, teacher_id, password, role, username)
VALUES (nextval('credentials_seq'), null, 1, '$2a$10$w/I292S9GLPxJdWQDXltmulmw01uWCV7R6VFlj6MMlyGeSbeCpeCq', 'TEACHER', 'test');
