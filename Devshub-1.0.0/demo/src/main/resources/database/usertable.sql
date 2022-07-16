DROP DATABASE IF EXISTS devshub;
CREATE DATABASE IF NOT EXISTS devshub;
USE devshub;


DROP TABLE IF EXISTS logindetails;
CREATE TABLE logindetails(
	email VARCHAR(20) NOT NULL PRIMARY KEY,
    passcode VARCHAR(20)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
	user_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
	firstName VARCHAR(20),
	lastName VARCHAR(20),
	location VARCHAR(20),
	message TEXT,
	image VARCHAR(40),
    color VARCHAR(10),
    email VARCHAR(20),
	FOREIGN KEY (email) REFERENCES logindetails (email)
);

DROP TABLE IF EXISTS education;
CREATE TABLE education (
	id integer auto_increment not null primary key,
    universityLocation varchar(50) not null,
    yearStarted	integer not null,
    yearEnded integer not null,
    courseStudied varchar(50) not null,
    degreeLevel varchar(50) not null,
    resultsAchieved varchar(10) not null,
	email VARCHAR(20) not null,
	FOREIGN KEY (email) REFERENCES logindetails (email)
);

DROP TABLE IF EXISTS experience;
CREATE TABLE experience ( 
	id integer auto_increment not null primary key,
	companyName varchar(30) not null,
    jobTitle varchar(30) not null,
    yearStarted integer not null,
    yearEnded integer not null,
    location varchar(30) not null,
	email VARCHAR(20) not null,
	FOREIGN KEY (email) REFERENCES logindetails (email)
);

DROP TABLE IF EXISTS messages;
CREATE TABLE messages(
    dt varchar(30),
    sender varchar(30),
    receiver varchar(30),
	message varchar(30)
);

SELECT 'INSERTING DATA INTO DATABASE' as 'INFO';

INSERT INTO logindetails VALUES ("admin", "password");
INSERT INTO users VALUES (null, "Lekan", "Adams", "Athlone", null, "person.png", "#cad07c", "admin");
INSERT INTO education VALUES (null, "AIT", 2017, 2020, "Computer Engineering", "Ordinary Level", "2.1 GPA", "admin");
INSERT INTO experience VALUES (null, "LMI Ericsson", "Junior Software Engineer", 2021, 2022, "Athlone", "admin");

select * from logindetails;
select * from users;
select * from education;
select * from experience;
select * from messages;

update users set message = 'Hi from the other side' where email = 'admin';


#select email from logindetails where email='admin';
#select * from users where email='joe';
#select * from logindetails where email="admin";