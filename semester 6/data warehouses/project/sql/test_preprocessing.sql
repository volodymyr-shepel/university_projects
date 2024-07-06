-- AFTER THIS COMMAND THERE WILL BE ALMOST 700000 of clean records
DELETE FROM Crimes_Weather.PreprocessingTable
WHERE NOT (
    vict_sex IN ('M', 'F')
    AND vict_descent IN ('A', 'B', 'C', 'D', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'O', 'P', 'S', 'U', 'V', 'W', 'Z')
    AND vict_age > 0
);

UPDATE Crimes_Weather.PreprocessingTable
SET temperature = (temperature - 32) * 5.0 / 9.0

UPDATE Crimes_Weather.PreprocessingTable
SET wind_chill = (wind_chill - 32) * 5.0 / 9.0

UPDATE Crimes_Weather.PreprocessingTable
SET wind_speed = wind_speed * 1.609344

-- Pressure in hPa=Pressure in inHg×33.8639
-- so now pressure in hPa
UPDATE Crimes_Weather.PreprocessingTable
SET pressure = pressure * 33.8639

-- now visibility in km
UPDATE Crimes_Weather.PreprocessingTable
SET visibility = visibility * 1.60934

-- Precipitation in millimeters
UPDATE Crimes_Weather.PreprocessingTable
SET precipitation = precipitation * 25.4

UPDATE Crimes_Weather.PreprocessingTable
SET weapon_desc = 'Unknown'
WHERE weapon_desc IS NULL OR weapon_desc = ''

UPDATE Crimes_Weather.PreprocessingTable
SET wind_direction = 'CALM'
WHERE wind_speed = 0


DELETE FROM Crimes_Weather.PreprocessingTable
WHERE premis_desc IS NULL OR premis_desc = ''


DELETE FROM Crimes_Weather.PreprocessingTable
WHERE weather_condition IS NULL OR weather_condition = ''




-- Number of records : 687159