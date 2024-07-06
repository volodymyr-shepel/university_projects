-- check number of null values
SELECT
    SUM(CASE WHEN date_occ IS NULL THEN 1 ELSE 0 END) AS date_occ_null_count,
    SUM(CASE WHEN area IS NULL THEN 1 ELSE 0 END) AS area_null_count,
    SUM(CASE WHEN area_name IS NULL OR area_name = '' THEN 1 ELSE 0 END) AS area_name_null_count,
    SUM(CASE WHEN crm_cd IS NULL THEN 1 ELSE 0 END) AS crm_cd_null_count,
    SUM(CASE WHEN crm_cd_desc IS NULL OR crm_cd_desc = '' THEN 1 ELSE 0 END) AS crm_cd_desc_null_count,
    SUM(CASE WHEN vict_age IS NULL THEN 1 ELSE 0 END) AS vict_age_null_count,
    SUM(CASE WHEN vict_sex IS NULL OR vict_sex = '' THEN 1 ELSE 0 END) AS vict_sex_null_count,
    SUM(CASE WHEN vict_descent IS NULL OR vict_descent = '' THEN 1 ELSE 0 END) AS vict_descent_null_count,
    SUM(CASE WHEN premis_cd IS NULL THEN 1 ELSE 0 END) AS premis_cd_null_count,
    SUM(CASE WHEN premis_desc IS NULL OR premis_desc = '' THEN 1 ELSE 0 END) AS premis_desc_null_count,
    SUM(CASE WHEN weapon_used_cd IS NULL THEN 1 ELSE 0 END) AS weapon_used_cd_null_count,
    SUM(CASE WHEN weapon_desc IS NULL OR weapon_desc = '' THEN 1 ELSE 0 END) AS weapon_desc_null_count,
    SUM(CASE WHEN location IS NULL OR location = '' THEN 1 ELSE 0 END) AS location_null_count,
    SUM(CASE WHEN lat IS NULL THEN 1 ELSE 0 END) AS lat_null_count,
    SUM(CASE WHEN lon IS NULL THEN 1 ELSE 0 END) AS lon_null_count,
    SUM(CASE WHEN weather_timestamp IS NULL THEN 1 ELSE 0 END) AS weather_timestamp_null_count,
    SUM(CASE WHEN temperature IS NULL THEN 1 ELSE 0 END) AS temperature_null_count,
    SUM(CASE WHEN wind_chill IS NULL THEN 1 ELSE 0 END) AS wind_chill_null_count,
    SUM(CASE WHEN humidity IS NULL THEN 1 ELSE 0 END) AS humidity_null_count,
    SUM(CASE WHEN pressure IS NULL THEN 1 ELSE 0 END) AS pressure_null_count,
    SUM(CASE WHEN visibility IS NULL THEN 1 ELSE 0 END) AS visibility_null_count,
    SUM(CASE WHEN wind_direction IS NULL OR wind_direction = '' THEN 1 ELSE 0 END) AS wind_direction_null_count,
    SUM(CASE WHEN wind_speed IS NULL THEN 1 ELSE 0 END) AS wind_speed_null_count,
    SUM(CASE WHEN precipitation IS NULL THEN 1 ELSE 0 END) AS precipitation_null_count,
    SUM(CASE WHEN weather_condition IS NULL OR weather_condition = '' THEN 1 ELSE 0 END) AS weather_condition_null_count,
    SUM(CASE WHEN sunrise_sunset IS NULL OR sunrise_sunset = '' THEN 1 ELSE 0 END) AS sunrise_sunset_null_count,
    SUM(CASE WHEN civil_twilight IS NULL OR civil_twilight = '' THEN 1 ELSE 0 END) AS civil_twilight_null_count,
    SUM(CASE WHEN nautical_twilight IS NULL OR nautical_twilight = '' THEN 1 ELSE 0 END) AS nautical_twilight_null_count,
    SUM(CASE WHEN astronomical_twilight IS NULL OR astronomical_twilight = '' THEN 1 ELSE 0 END) AS astronomical_twilight_null_count
FROM
    Crimes_Weather.PreprocessingTable;

SELECT COUNT(*) FROM Crimes_Weather.PreprocessingTable;

SELECT COUNT(*) 
FROM Crimes_Weather.PreprocessingTable
WHERE
vict_sex IN ('M', 'F')
AND vict_descent IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W', 'Z')
AND vict_age > 0


SELECT COUNT(*) 
FROM Crimes_Weather.PreprocessingTable
WHERE NOT (
    vict_sex IN ('M', 'F')
    AND vict_descent IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W', 'Z')
    AND vict_age > 0
);

SELECT TOP 5 * FROM Crimes_Weather.PreprocessingTable ;

-- crm_cd
SELECT DISTINCT crm_cd
FROM Crimes_Weather.PreprocessingTable  

SELECT crm_cd, COUNT(*) AS count
FROM Crimes_Weather.PreprocessingTable 
GROUP BY crm_cd;

SELECT COUNT(DISTINCT crm_cd) AS distinct_crm_cd_count
FROM Crimes_Weather.PreprocessingTable ;

SELECT * FROM Crimes_Weather.PreprocessingTable  WHERE crm_cd_desc IS NULL OR crm_cd_desc = ''
SELECT * FROM Crimes_Weather.PreprocessingTable  WHERE crm_cd IS NULL OR crm_cd = ''

-- ok so there is no missing values in crm_cd and crm_cd_desc
-- check if there is categpry where gender is not set

SELECT crm_cd,crm_cd_desc,COUNT(*)
FROM Crimes_Weather.PreprocessingTable 
WHERE (vict_sex IS NULL OR vict_sex = '') AND vict_age <= 0
GROUP BY crm_cd,crm_cd_desc
-- So in majority of cases when we do not know vict_sex we also do not have also vict_age

SELECT crm_cd,crm_cd_desc,COUNT(*)
FROM Crimes_Weather.PreprocessingTable 
WHERE (vict_sex IS NULL OR vict_sex = '') AND vict_age <= 0 AND (vict_descent IS NULL OR vict_descent = '')
GROUP BY crm_cd,crm_cd_desc

SELECT crm_cd_desc,COUNT(*)
FROM Crimes_Weather.PreprocessingTable 
WHERE 
vict_sex NOT IN ('M', 'F', 'X')
AND 
vict_descent NOT IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W', 'X', 'Z')
AND 
vict_age <= 0
GROUP BY crm_cd_desc


SELECT crm_cd_desc,COUNT(*)
FROM Crimes_Weather.PreprocessingTable 
WHERE 
vict_sex NOT IN ('M', 'F')
AND 
vict_descent NOT IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W','Z')
AND 
vict_age <= 0
GROUP BY crm_cd_desc

SELECT COUNT(*)
FROM Crimes_Weather.PreprocessingTable 

SELECT COUNT(*)
FROM Crimes_Weather.PreprocessingTable 
WHERE 
vict_sex NOT IN ('M', 'F', 'X')
AND 
vict_descent NOT IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W', 'X', 'Z')
AND 
vict_age <= 0

-- FILTER ALL THE DATA WHICH HAS MISSING VALUES ??? SO WE HAVE ALMOST 700000 OF CLEAN RECORDS(EVEN WITHOUT UNKNOWN)
SELECT COUNT(*)
FROM Crimes_Weather.PreprocessingTable 
WHERE 
vict_sex IN ('M', 'F')
AND 
vict_descent IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W', 'Z')
AND 
vict_age > 0

DELETE FROM Crimes_Weather.PreprocessingTable
WHERE NOT (
    vict_sex IN ('M', 'F')
    AND vict_descent IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W', 'Z')
    AND vict_age > 0
);


-- IT IS BETTER NOT TO DROP weapon_used_cd where <= 0 because there are almost 350000 such records
SELECT COUNT(*) FROM Crimes_Weather.PreprocessingTable
WHERE weapon_used_cd > 0


SELECT * FROM Crimes_Weather.PreprocessingTable   
WHERE vict_age <= 0

SELECT 'is not null', COUNT(*) FROM Crimes_Weather.PreprocessingTable   
WHERE crm_cd_desc = 'VEHICLE - STOLEN' AND vict_sex IS NOT NULL AND vict_sex <> ''

UNION 
SELECT 'is null',COUNT(*) FROM Crimes_Weather.PreprocessingTable   
WHERE crm_cd_desc = 'VEHICLE - STOLEN' 

SELECT * FROM Crimes_Weather.PreprocessingTable   
WHERE crm_cd_desc = 'VEHICLE - STOLEN' AND vict_age <> 0

SELECT DISTINCT vict_age
FROM Crimes_Weather.PreprocessingTable   
ORDER BY vict_age

SELECT COUNT(*), vict_age
FROM Crimes_Weather.PreprocessingTable 
GROUP BY vict_age
ORDER BY vict_age

SELECT *   
FROM Crimes_Weather.PreprocessingTable 
WHERE vict_age < 0

SELECT DISTINCT vict_sex
FROM Crimes_Weather.PreprocessingTable 

SELECT vict_sex,COUNT(*) vict_sex_count 
FROM Crimes_Weather.PreprocessingTable 
GROUP BY vict_sex

-- For weapon used a lot of records with code 0 (Unknown)
SELECT weapon_used_cd,COUNT(*)
FROM Crimes_Weather.PreprocessingTable 
GROUP BY weapon_used_cd

SELECT COUNT(*),
FROM Crimes_Weather.PreprocessingTable 
WHERE weapon_used_cd = 0
GROUP BY weapon_desc

SELECT * FROM Crimes_Weather.PreprocessingTable  WHERE vict_sex = 'H';


SELECT * FROM 
Crimes_Weather.PreprocessingTable
WHERE wind_direction IS NULL OR wind_direction = ''
SELECT COUNT(*) 
FROM Crimes_Weather.PreprocessingTable
WHERE weather_condition is NULL OR weather_condition = ''

SELECT *
FROM Crimes_Weather.PreprocessingTable
WHERE premis_desc is NULL OR premis_desc = ''


SELECT *
FROM Crimes_Weather.PreprocessingTable
WHERE premis_cd = 418 AND (premis_desc IS NOT NULL AND premis_desc <> '') 

SELECT * FROM 
Crimes_Weather.PreprocessingTable
WHERE wind_speed = 0
AND wind_direction <> 'CALM' AND wind_direction IS NOT NULL AND wind_direction <> ''
