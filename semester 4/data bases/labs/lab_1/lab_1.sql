--DROP DATABASE lab_1;
CREATE DATABASE lab_1;
USE lab_1;



CREATE TABLE Author(
	Author_ID INT PRIMARY KEY IDENTITY(1,1),
	First_name varchar(50) NOT NULL,
	Last_name VARCHAR(50) NOT NULL
);

CREATE TABLE Genre(
	genre_id INT PRIMARY KEY IDENTITY(1,1),
	genre_name VARCHAR(100),
	super_genre_id INT
);

CREATE TABLE Category(
	category_name VARCHAR(100) PRIMARY KEY,
	category_description VARCHAR(200)
);
CREATE TABLE Publisher(
	Publisher_ID INT PRIMARY KEY IDENTITY(1,1),
	Publisher_name VARCHAR(50) NOT NULL,
	contact_info VARCHAR(200) NOT NULL,
	office_location VARCHAR(200) NOT NULL,
);

CREATE TABLE Person(
	person_id INT PRIMARY KEY IDENTITY(1,1),
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	
);

CREATE TABLE Book(
	ISBN VARCHAR(20) PRIMARY KEY,
	Name VARCHAR(100) NOT NULL,
	Category VARCHAR(100) NOT NULL,
	Book_Format VARCHAR(50) NOT NULL,
	Publisher_ID INT,
	Published DATE NOT NULL,
	Pages INT NOT NULL,
	genre_id INT NOT NULL,
	category_name VARCHAR(100) NOT NULL,

	FOREIGN KEY(Publisher_ID) REFERENCES Publisher(Publisher_ID),
	FOREIGN KEY(genre_id) REFERENCES Genre(genre_id),
	FOREIGN KEY(category_name) REFERENCES Category(category_name)
	
);

CREATE TABLE BookAuthor(
	ISBN VARCHAR(20),
	Author_ID INT,
	FOREIGN KEY(ISBN) REFERENCES Book(ISBN),
	FOREIGN KEY(Author_ID) REFERENCES Author(Author_ID)
);


CREATE TABLE PersonBook(
	person_id INT PRIMARY KEY IDENTITY(1,1),
	ISBN VARCHAR(20),
	book_status VARCHAR(50) NOT NULL,
	grade INT,
	FOREIGN KEY(ISBN) REFERENCES Book(ISBN),
	FOREIGN KEY(person_id) REFERENCES Person(person_id),
	CONSTRAINT CHK_Status CHECK (book_status IN ('read','reading','will read')),
	CONSTRAINT CHK_Grade CHECK (grade >= 1 AND grade <=5)
);

