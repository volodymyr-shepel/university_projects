CREATE DATABASE task_3;
--DROP DATABASE task_3;
USE task_3;

DECLARE @var1 INT
SET @var1 = 3;


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

-- TASK 1
CREATE TABLE University_Year(
	university_year_id INT IDENTITY(1,1) PRIMARY KEY,
	university_id INT NOT NULL,
	year INT NOT NULL,
	num_students INT NOT NULL,
	student_staff_ratio FLOAT NOT NULL,
	FOREIGN KEY (university_id) REFERENCES University(id),
	CONSTRAINT year_check CHECK (year > 1700),
	CONSTRAINT students_num_check CHECK (num_students >= 0)
	
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
	university_year_id INT NOT NULL,
	score INT NOT NULL,
	FOREIGN KEY(university_id) REFERENCES University(id),
	FOREIGN KEY (ranking_criteria_id) REFERENCES Ranking_Criteria (id),
	FOREIGN KEY (university_year_id) REFERENCES University_Year(university_year_id) ON DELETE CASCADE,
	PRIMARY KEY(university_id,ranking_criteria_id,university_year_id)

);


-- TASK 4

--year check
ALTER TABLE University_Year
DROP CONSTRAINT year_check;

ALTER TABLE University_Year
ADD CONSTRAINT year_check CHECK (year > 1800); 


-- score check
ALTER TABLE University_Ranking_Year
ADD CONSTRAINT score_check CHECK (score >= 0 AND score <= 10); 	


-- INSERT VALUES

INSERT INTO Country(country_name)
VALUES('Spain'),('Poland'),('France'),('Germany'),('England');

INSERT INTO University(country_id,university_name)
VALUES(1,'Universitat de Barcelona'),
(2,'Wroclaw university of Science and technology'),
(3,'Ecole Polytechnique'),
(4,'Technical University of Munich'),
(5,'Oxford');

INSERT INTO University_Year(university_id,year,num_students,student_staff_ratio)
VALUES 
(1,1999,1000,2.8),
(2,2003,1777,2.73),
(3,2023,2636,3.23),
(4,2012,2001,2.67),
(5,2018,2225,3.01);



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
VALUES (2,3,1,9),
(4,5,2,8),
(5,1,3,7),
(1,4,4,5),
(3,2,5,3);

-- QUERIES

--TASK 2

SELECT id AS university_id,university_name AS name FROM University; 

-- TASK 3

ALTER TABLE Country
ADD country_address VARCHAR(100);

ALTER TABLE Country
ALTER COLUMN country_name VARCHAR(100);


-- TASK 5

DELETE FROM University_Year
WHERE university_year_id = @var1;

-- TASK 6
UPDATE University
SET university_name = 'Wroclaw University of Science and Technology'
WHERE id = @var1;


SELECT * FROM University_Year;

