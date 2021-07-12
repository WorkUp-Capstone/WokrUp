USE workup_db;
INSERT INTO role(role)
VALUES ('owner'), ('developer');

INSERT INTO categories(name)
VALUES ('Front End Development'), ('Back End Development'), ('Full Stack Development'), ('MySQL'), ('CSS'), ('HTML'), ('Spring'), ('Java'), ('JQuery'), ('JavaScript'), ('AJAX'), ('Authentication'), ('Email service'), ('Database'), ('UI/UX'), ('RESTful API');

/* Run migration.sql first */