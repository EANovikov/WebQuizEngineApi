INSERT INTO user (email, password)
VALUES ('user1@test.com', '$2a$10$RRNGgJUeR4czObEY17ul5emHSomUZ3v2RInJx0uCPiAelfso9JEf2');
INSERT INTO user (email, password)
VALUES ('user2@test.com', '$2a$10$uHABEw8LwFiFzuu15AN8C.86RV36EglwAODy35FAmATNAlLAf43be');
COMMIT;

INSERT INTO question (id, title, text, author)
VALUES (1, 'question1', 'text1', 'user1@test.com');
INSERT INTO question (id, title, text, author)
VALUES (2, 'question2', 'text2', 'user2@test.com');
INSERT INTO question (id, title, text, author)
VALUES (3, 'question3', 'text3', 'user1@test.com');


INSERT INTO question_answer (question_id, answer)
VALUES (1, 0);
INSERT INTO question_answer (question_id, answer)
VALUES (1, 1);
INSERT INTO question_answer (question_id, answer)
VALUES (2, 2);
INSERT INTO question_answer (question_id, answer)
VALUES (3, 0);

INSERT INTO question_options (question_id, options)
VALUES (1, 'a');
INSERT INTO question_options (question_id, options)
VALUES (1, 'b');
INSERT INTO question_options (question_id, options)
VALUES (1, 'c');
INSERT INTO question_options (question_id, options)
VALUES (1, 'd');
INSERT INTO question_options (question_id, options)
VALUES (2, 'a');
INSERT INTO question_options (question_id, options)
VALUES (2, 'b');
INSERT INTO question_options (question_id, options)
VALUES (3, 'a');
INSERT INTO question_options (question_id, options)
VALUES (3, 'b');


INSERT INTO question_complete (complete_id, user, question_id, completed_at)
VALUES (1, 'user1@test.com', 1, '2020-07-11 08:25:50.000000');
INSERT INTO question_complete (complete_id, user, question_id, completed_at)
VALUES (2, 'user1@test.com', 2, '2020-07-11 08:25:51.000000');
