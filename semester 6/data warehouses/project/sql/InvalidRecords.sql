INSERT INTO dbo.InvalidRecords
SELECT *
FROM StagingTable
WHERE NOT (
    vict_sex IN ('M', 'F')
    AND vict_descent IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W', 'Z')
    AND vict_age > 0
);

DELETE FROM StagingTable
WHERE NOT (
    vict_sex IN ('M', 'F')
    AND vict_descent IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W', 'Z')
    AND vict_age > 0
);

INSERT INTO dbo.InvaludRecords
SELECT * FROM StagingTable
WHERE premis_desc IS NULL OR premis_desc = '';

INSERT INTO  dbo.InvalidRecords
SELECT * FROM StagingTable
WHERE weather_condition IS NULL OR weather_condition = '';


IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES 
               WHERE TABLE_SCHEMA = 'dbo' 
                 AND TABLE_NAME = 'InvalidRecords')
BEGIN
CREATE TABLE dbo.InvalidRecords (
crime_id INT,
        date_occ DATETIME,
        area SMALLINT,
        area_name VARCHAR(50),
        crm_cd SMALLINT,
        crm_cd_desc VARCHAR(MAX),
        vict_age SMALLINT,
        vict_sex VARCHAR(1),
        vict_descent VARCHAR(1),
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
END;
