CREATE DATABASE task_3;
--DROP DATABASE task_3;
USE task_3;

CREATE TABLE Country(
	id INT IDENTITY(1,1) PRIMARY KEY,
	country_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE University(
	id INT IDENTITY(1,1) PRIMARY KEY,
	country_id INT NOT NULL,
	university_name VARCHAR(50) NOT NULL, -- Heidelberg university
	FOREIGN KEY (country_id) REFERENCES Country(id)
);


CREATE TABLE Ranking_System(
	id INT PRIMARY KEY IDENTITY(1,1),
	system_name VARCHAR(150) NOT NULL
);



CREATE TABLE Ranking_Criteria(
	id INT PRIMARY KEY IDENTITY(1,1),
	ranking_system_id INT NOT NULL,
	criteria_name VARCHAR(50) NOT NULL,
	FOREIGN KEY (ranking_system_id) REFERENCES Ranking_System(id)
);
CREATE TABLE University_Ranking_Year(
	university_id INT ,
	ranking_criteria_id INT,
	university_year INT NOT NULL,
	score INT NOT NULL,
	FOREIGN KEY (university_id) REFERENCES University (id),
	FOREIGN KEY (ranking_criteria_id) REFERENCES RAnking_Criteria (id),
	CONSTRAINT pk_university_year PRIMARY KEY(university_id,ranking_criteria_id)

);


INSERT INTO Country(country_name)
VALUES('Spain'),('Poland'),('France'),('Germany'),('England');

INSERT INTO University(country_id,university_name)
VALUES(1,'Universitat de Barcelona'),
(2,'Wroclaw university of Science and technology'),
(3,'Ecole Polytechnique'),
(4,'Technical University of Munich'),
(5,'Oxford');


INSERT INTO Ranking_System(system_name) 
VALUES ('QS World University Rankings'),
('Times Higher Education World University Rankings'),
('Academic Ranking of World Universities'),
('U.S. News & World Report Best Global Universities Rankings'),
('CWTS Leiden Ranking');

INSERT INTO Ranking_Criteria(ranking_system_id,criteria_name)
VALUES (3,'Internationalization'),
(1,'Reputation'),
(5,'Teaching quality'),
(4,'Social impact'),
(2,'Research output');

INSERT INTO University_Ranking_Year
VALUES (2,3,2019,90),
(4,5,2020,85),
(5,1,2021,76),
(1,4,2022,56),
(3,2,2023,31);

-- QUERIES

SELECT * FROM University_Ranking_Year
WHERE score > 80;

SELECT University.university_name,Country.country_name
FROM University
INNER JOIN Country ON University.country_id = Country.id;


SELECT University.university_name,Country.country_name,University_Ranking_Year.university_year,University_Ranking_Year.score,Ranking_Criteria.criteria_name
FROM University
INNER JOIN Country ON University.country_id = Country.id
INNER JOIN University_Ranking_Year ON University.id = University_Ranking_Year.university_id
INNER JOIN Ranking_Criteria ON University_Ranking_Year.ranking_criteria_id = Ranking_Criteria.id;
