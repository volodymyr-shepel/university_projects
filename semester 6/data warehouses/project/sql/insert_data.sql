-- Insert into DIM_Area
INSERT INTO Crimes_Weather.DIM_Area (area_id, area_name)
SELECT DISTINCT
    area,
    area_name
FROM Crimes_Weather.TempTable;

-- Insert into DIM_Weather_Condition with unique weather conditions
INSERT INTO Crimes_Weather.DIM_Weather_Condition (weather_condition_id, weather_condition)
SELECT
    ROW_NUMBER() OVER (ORDER BY weather_condition) AS weather_condition_id,
    weather_condition
FROM (
    SELECT DISTINCT
        weather_condition
    FROM Crimes_Weather.TempTable
) AS distinct_conditions;


-- Insert into DIM_Premise
INSERT INTO Crimes_Weather.DIM_Premise (premise_id, premise_description)
SELECT DISTINCT
    premis_cd,
    premis_desc
FROM Crimes_Weather.TempTable;


-- Insert into DIM_Crime
INSERT INTO Crimes_Weather.DIM_Crime (crime_code, crime_description)
SELECT DISTINCT
    crm_cd,
    crm_cd_desc
FROM Crimes_Weather.TempTable;


-- Insert into DIM_Weapon_Used
INSERT INTO Crimes_Weather.DIM_Weapon_Used (weapon_code, weapon_description)
SELECT DISTINCT
    weapon_used_cd,
    weapon_desc
FROM Crimes_Weather.TempTable;


-- Insert into DIM_Time
INSERT INTO Crimes_Weather.DIM_Time (PK_TIME, year, month, weekday, day_of_month, hour, season)
SELECT 
    CAST(FORMAT(date_occ, 'yyyyMMddHHmm') AS BIGINT) AS PK_TIME,
    YEAR(date_occ) AS year,
    MONTH(date_occ) AS month,
    DATEPART(WEEKDAY, date_occ) AS weekday,
    DAY(date_occ) AS day_of_month,
    DATEPART(HOUR, date_occ) AS hour,
    CASE
        WHEN MONTH(date_occ) IN (12, 1, 2) THEN 1
        WHEN MONTH(date_occ) IN (3, 4, 5) THEN 2
        WHEN MONTH(date_occ) IN (6, 7, 8) THEN 3
        WHEN MONTH(date_occ) IN (9, 10, 11) THEN 4
    END AS season
FROM (
    SELECT DISTINCT date_occ
    FROM Crimes_Weather.TempTable
) AS distinct_dates;


-- INSERT INTO FACT_Crimes
INSERT INTO Crimes_Weather.FACT_Crimes (
    pressure,
    humidity,
    wind_speed,
    visibility,
    weather_condition,
    sunrise_sunset,
    area,
    premise,
	crime,
    date_occured,
    victim_descent,
    victim_sex,
    victim_age,
    weapon_code
)
SELECT 
    CAST(tt.pressure AS SMALLINT),
    CAST(tt.humidity AS SMALLINT),
    tt.wind_speed,
    CAST(tt.visibility AS SMALLINT),
    wc.weather_condition_id,
    ss.id,
    a.area_id,
    p.premise_id,
	c.crime_code,
    CAST(FORMAT(tt.date_occ, 'yyyyMMddHHmm') AS BIGINT) AS date_occured,
    d.descent,
    s.sex,
    tt.vict_age,
    w.weapon_code
FROM Crimes_Weather.TempTable tt
INNER JOIN Crimes_Weather.DIM_Weather_Condition wc ON tt.weather_condition = wc.weather_condition
INNER JOIN Crimes_Weather.DIM_Sunrise_Sunset ss ON tt.sunrise_sunset = ss.name
INNER JOIN Crimes_Weather.DIM_Area a ON tt.area_name = a.area_name
INNER JOIN Crimes_Weather.DIM_Premise p ON tt.premis_cd = p.premise_id
INNER JOIN Crimes_Weather.DIM_Descent d ON tt.vict_descent = d.descent
INNER JOIN Crimes_Weather.DIM_Sex s ON tt.vict_sex = s.sex
INNER JOIN Crimes_Weather.DIM_Weapon_Used w ON tt.weapon_used_cd = w.weapon_code
INNER JOIN Crimes_Weather.DIM_Crime c ON tt.crm_cd = c.crime_code;
