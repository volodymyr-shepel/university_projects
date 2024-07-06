CREATE TABLE Crimes_Weather.PreprocessingTable (
    date_occ DATETIME,
    area SMALLINT,
    area_name VARCHAR(50),
    crm_cd SMALLINT,
    crm_cd_desc VARCHAR(MAX),
    vict_age SMALLINT,
    vict_sex varchar(1),
	vict_descent varchar(1),
    premis_cd FLOAT,
    premis_desc VARCHAR(MAX),
    weapon_used_cd FLOAT,
    weapon_desc VARCHAR(MAX),
    location VARCHAR(50),
    lat FLOAT,
    lon FLOAT,
    weather_timestamp DATETIME,
    temperature FLOAT,
    wind_chill FLOAT,
    humidity FLOAT,
    pressure FLOAT,
    visibility FLOAT,
    wind_direction VARCHAR(10),
    wind_speed FLOAT,
    precipitation FLOAT,
    weather_condition VARCHAR(MAX),
    sunrise_sunset VARCHAR(10),
    civil_twilight VARCHAR(10),
    nautical_twilight VARCHAR(10),
    astronomical_twilight VARCHAR(10)
);

INSERT INTO Crimes_Weather.PreprocessingTable
SELECT *
FROM Crimes_Weather.TempTable;


