USE workup_db;
INSERT INTO role(role)
VALUES ('owner'), ('developer');

INSERT INTO status(status)
VALUES ('open'), ('in progress');

INSERT INTO role(role)
VALUES('owner'), ('developer');

INSERT INTO categories(name)
VALUES ('Front_end_development'), ('Back_end_development'), ('Full_stack_development'), ('MySQL'), ('CSS'), ('HTML'), ('Spring'), ('Java'), ('JQuery'), ('JavaScript'), ('AJAX'), ('Authentication'), ('Email_service'), ('Database'), ('UI_UX'), ('RESTful_API');

/* Run migration.sql first */