CREATE TABLE Crimes_Weather.Helper_Month (
    month_number SMALLINT PRIMARY KEY,
    month_name VARCHAR(100) 
);


INSERT INTO Crimes_Weather.Helper_Month (month_number, month_name)
VALUES
    (1, 'January'),
    (2, 'February'),
    (3, 'March'),
    (4, 'April'),
    (5, 'May'),
    (6, 'June'),
    (7, 'July'),
    (8, 'August'),
    (9, 'September'),
    (10, 'October'),
    (11, 'November'),
    (12, 'December');

-- Helper_Weekday
CREATE TABLE  Crimes_Weather.Helper_Weekday (
    weekday_number SMALLINT PRIMARY KEY,
    weekday_name VARCHAR(100)
);


INSERT INTO Crimes_Weather.Helper_Weekday (weekday_number, weekday_name)
VALUES
    (1, 'Sunday'),
    (2, 'Monday'),
    (3, 'Tuesday'),
    (4, 'Wednesday'),
    (5, 'Thursday'),
    (6, 'Friday'),
    (7, 'Saturday');

-- Helper_Season
-- Using season for hierarchy

CREATE TABLE  Crimes_Weather.Helper_Season (
    season_number SMALLINT PRIMARY KEY,
    season_name VARCHAR(100)
);

INSERT INTO Crimes_Weather.Helper_Season (season_number, season_name)
VALUES
    (1, 'Winter'),
	(2, 'Spring'),
	(3, 'Summer'),
	(4, 'Autumn')

CREATE TABLE Crimes_Weather.DIM_Sunrise_Sunset (
    id CHAR PRIMARY KEY,
    name VARCHAR(100) 
);

INSERT INTO Crimes_Weather.DIM_Sunrise_Sunset(id, name)
VALUES
    ('D','Day'),
	('N','Night')

-- Descent
CREATE TABLE  Crimes_Weather.DIM_Descent (
    descent CHAR PRIMARY KEY,
    descent_description VARCHAR(100)
);

INSERT INTO Crimes_Weather.DIM_Descent (descent, descent_description)
VALUES
    ('A', 'Other Asian'),
    ('B', 'Black'),
    ('C', 'Chinese'),
    ('D', 'Cambodian'),
    ('F', 'Filipino'),
    ('G', 'Guamanian'),
    ('H', 'Hispanic/Latin/Mexican'),
    ('I', 'American Indian/Alaskan Native'),
    ('J', 'Japanese'),
    ('K', 'Korean'),
    ('L', 'Laotian'),
    ('O', 'Other'),
    ('P', 'Pacific Islander'),
    ('S', 'Samoan'),
    ('U', 'Hawaiian'),
    ('V', 'Vietnamese'),
    ('W', 'White'),
    ('Z', 'Asian Indian');


-- Sex
CREATE TABLE  Crimes_Weather.DIM_Sex (
    sex CHAR PRIMARY KEY,
    sex_descirption VARCHAR(100)
);

INSERT INTO Crimes_Weather.DIM_Sex(sex, sex_descirption)
VALUES
    ('M','Male'),
	('F','Female')

-- Weapon Used
CREATE TABLE Crimes_Weather.DIM_Weapon_Used (
    weapon_code SMALLINT PRIMARY KEY,
    weapon_description VARCHAR(100)
);


-- Premise
CREATE TABLE  Crimes_Weather.DIM_Premise (
    premise_id SMALLINT PRIMARY KEY,
	premise_description VARCHAR(100)
);

-- Area
CREATE TABLE  Crimes_Weather.DIM_Area (
    area_id SMALLINT PRIMARY KEY,
	area_name VARCHAR(100)
);


-- Weather Condition
CREATE TABLE  Crimes_Weather.DIM_Weather_Condition (
    weather_condition_id SMALLINT PRIMARY KEY,
	weather_condition VARCHAR(100)
);



CREATE TABLE Crimes_Weather.DIM_Time(
	PK_TIME BIGINT PRIMARY KEY,
    year INT,
    month SMALLINT FOREIGN KEY REFERENCES Crimes_Weather.Helper_Month(month_number),
    weekday SMALLINT FOREIGN KEY REFERENCES Crimes_Weather.Helper_Weekday(weekday_number),
    day_of_month SMALLINT,
	hour SMALLINT,
	season SMALLINT FOREIGN KEY REFERENCES Crimes_Weather.Helper_Season(season_number),
);

-- Crime
CREATE TABLE Crimes_Weather.DIM_Crime(
	crime_code SMALLINT PRIMARY KEY,
	crime_description VARCHAR(100)
);


CREATE TABLE Crimes_Weather.FACT_Crimes(
	crime_id INT IDENTITY(1,1) PRIMARY KEY,
	pressure SMALLINT,
	humidity SMALLINT,
	wind_speed FLOAT, -- user FLOAT because we change it into kmh
	visibility SMALLINT,
	weather_condition SMALLINT FOREIGN KEY REFERENCES Crimes_Weather.DIM_Weather_Condition(weather_condition_id),
	sunrise_sunset CHAR FOREIGN KEY REFERENCES Crimes_Weather.DIM_Sunrise_Sunset(id),
	area SMALLINT FOREIGN KEY REFERENCES Crimes_Weather.DIM_Area(area_id),
	premise  SMALLINT FOREIGN KEY REFERENCES Crimes_Weather.DIM_Premise(premise_id),
	crime SMALLINT FOREIGN KEY REFERENCES Crimes_Weather.DIM_Crime(crime_code),
	date_occured BIGINT FOREIGN KEY REFERENCES Crimes_Weather.DIM_Time(PK_TIME),
	victim_descent CHAR FOREIGN KEY REFERENCES Crimes_Weather.DIM_Descent(descent),
	victim_sex CHAR FOREIGN KEY REFERENCES Crimes_Weather.DIM_Sex(sex),
	victim_age SMALLINT,
	weapon_code SMALLINT FOREIGN KEY REFERENCES Crimes_Weather.DIM_Weapon_Used(weapon_code)

)
