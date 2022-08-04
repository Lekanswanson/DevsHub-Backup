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
	id integer auto_increment not null primary key,
    dt varchar(30),
    sender varchar(30),
    receiver varchar(30),
	message varchar(30),
    size integer
);

DROP TABLE IF EXISTS articles;
CREATE TABLE articles(
	id integer auto_increment not null primary key,
    title varchar(50) not null,
	details varchar(200) not null,
    url varchar(100) not null,
    likes integer
);

DROP TABLE IF EXISTS articleComments;
CREATE TABLE articleComments(
	id integer auto_increment not null primary key
);


DROP TABLE IF EXISTS memberlikes;
CREATE TABLE memberlikes(
	email varchar(30) not null,
    articleId integer not null,
    foreign key (articleId) references articles (id)
);

DROP TABLE IF EXISTS projects;
CREATE TABLE projects(
	title varchar(50) not null,
	details text,
	lang varchar(50),
    technology varchar(50),
    video varchar(50),
    email varchar(30) not null,
	foreign key (email) references logindetails (email)
);

DROP TABLE IF EXISTS languages;
CREATE TABLE languages(
	id integer auto_increment not null primary key,
    languageName varchar(50) not null
);

DROP TABLE IF EXISTS memberLanguages;
CREATE TABLE memberLanguages(
	id integer auto_increment not null primary key,
    languageName varchar(50) not null,
    yearsExperience varchar(50) not null,
	email VARCHAR(20) not null,
	FOREIGN KEY (email) REFERENCES logindetails (email)
);

SELECT 'INSERTING DATA INTO DATABASE' as 'INFO';

INSERT INTO logindetails VALUES ("admin", "password");
INSERT INTO users VALUES (null, "Lekan", "Adams", "Athlone", null, "person.png", "#cad07c", "admin");
update users set message = 'Hi from the other side' where email = 'admin';
INSERT INTO education VALUES (null, "AIT", 2017, 2020, "Computer Engineering", "Ordinary Level", "2.1 GPA", "admin");
INSERT INTO experience VALUES (null, "LMI Ericsson", "Junior Software Engineer", 2021, 2022, "Athlone", "admin");
insert into projects values ("Cards Game", "Game of switch i created with java", "Java_Javascript_", "Docker_Github_", "", "admin");

INSERT INTO articles VALUES (null, "What is Maven: Here's What You Need to Know", "Interactive maven tutorial", "https://www.simplilearn.com/tutorials/maven-tutorial/what-is-maven", 0);
INSERT INTO articles VALUES (null, "Docker", "Docker is an open source platform for building, deploying, and managing containerized applications. Learn about containers, how they compare to VMs, and why Docker is so widely adopted and used.", "https://www.ibm.com/cloud/learn/docker", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "Docker", "Docker is an open source platform for building, deploying, and managing containerized applications. Learn about containers, how they compare to VMs, and why Docker is so widely adopted and used.", "https://www.ibm.com/cloud/learn/docker", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "Docker", "Docker is an open source platform for building, deploying, and managing containerized applications. Learn about containers, how they compare to VMs, and why Docker is so widely adopted and used.", "https://www.ibm.com/cloud/learn/docker", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "Docker", "Docker is an open source platform for building, deploying, and managing containerized applications. Learn about containers, how they compare to VMs, and why Docker is so widely adopted and used.", "https://www.ibm.com/cloud/learn/docker", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "Docker", "Docker is an open source platform for building, deploying, and managing containerized applications. Learn about containers, how they compare to VMs, and why Docker is so widely adopted and used.", "https://www.ibm.com/cloud/learn/docker", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "Docker", "Docker is an open source platform for building, deploying, and managing containerized applications. Learn about containers, how they compare to VMs, and why Docker is so widely adopted and used.", "https://www.ibm.com/cloud/learn/docker", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "Docker", "Docker is an open source platform for building, deploying, and managing containerized applications. Learn about containers, how they compare to VMs, and why Docker is so widely adopted and used.", "https://www.ibm.com/cloud/learn/docker", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);
INSERT INTO articles VALUES (null, "What is Jenkins? The CI server explained", "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories", "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0);


INSERT INTO languages VALUES (null, "HTML");
INSERT INTO languages VALUES (null, "CSS");
INSERT INTO languages VALUES (null, "Python");
INSERT INTO languages VALUES (null, "Java");
INSERT INTO languages VALUES (null, "JavaScript");
INSERT INTO languages VALUES (null, "C#");
INSERT INTO languages VALUES (null, "C++");
INSERT INTO languages VALUES (null, "Swift");
INSERT INTO languages VALUES (null, "R");
INSERT INTO languages VALUES (null, "Bash");
INSERT INTO languages VALUES (null, "Golang (Go)");
INSERT INTO languages VALUES (null, "Ruby");
INSERT INTO languages VALUES (null, "Groovy");
INSERT INTO languages VALUES (null, "Kotlin");
INSERT INTO languages VALUES (null, "TypeScript");
INSERT INTO languages VALUES (null, "PHP");
INSERT INTO languages VALUES (null, "Rust");
INSERT INTO languages VALUES (null, "Shell");
INSERT INTO languages VALUES (null, "Visual Basic .NET");
INSERT INTO languages VALUES (null, "PowerShell");
INSERT INTO languages VALUES (null, "Scala");
INSERT INTO languages VALUES (null, "Perl");
INSERT INTO languages VALUES (null, "Haskell");
INSERT INTO languages VALUES (null, "SQL");
INSERT INTO languages VALUES (null, "MATLAB");
INSERT INTO languages VALUES (null, "Delphi");
INSERT INTO languages VALUES (null, "Lua");
INSERT INTO languages VALUES (null, "MongoDB");


select * from logindetails;
select * from users;
select * from education;
select * from experience;
select * from messages;
select * from articles;
select * from memberlikes;
select * from projects;
select * from languages;
select * from memberLanguages;


#delete from messages where id = 26;
#select * from messages where sender='jack' or sender='joe';
#update messages set size=16 where id=32;
#update users set image="person.png" where email="admin";
#delete from users where email = 'liam@yahoo.com';
#select email from users where email like '%a%';
#select languageName from languages where languageName like '%j%';
#delete from logindetails where email = 'liam@yahoo.com';
#insert into messages values (null, null, 'admin', 'joe', 'Hi joe', 0);
#update messages set size=0 where id=1 and sender='admin' and receiver='joe' and message='Hi joe';
#update articles set likes=likes-1 where id=1;
#delete from memberlikes where email='admin' and articleId=1;
#update users set message = null where email = 'admin';
#select email from logindetails where email='admin';
#select * from users where email='joe';
#select * from logindetails where email="admin";