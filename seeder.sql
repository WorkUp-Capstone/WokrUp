USE workup_db;
INSERT INTO role(role)
VALUES ('owner'), ('developer');

INSERT INTO categories(name)
VALUES ('Front End Development'), ('Back End Development'), ('Full Stack Development'), ('MySQL'), ('CSS'), ('HTML'), ('Spring'), ('Java'), ('JQuery'), ('JavaScript'), ('AJAX'), ('Authentication'), ('Email_service'), ('Database'), ('UI_UX'), ('RESTful API');

/* Run migration.sql first */

UPDATE categories
SET
    'name'
WHERE
    'Front_end_development' = 'Front End Development',
    'Back_end_development' = 'Back End Development',
    'Full_stack_development' = 'Full Stack Development';