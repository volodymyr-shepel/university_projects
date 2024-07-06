CREATE TRIGGER trg_Insert_DIM_Weapon_Used
ON Crimes_Weather.DIM_Weapon_Used
AFTER INSERT
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    INSERT INTO History_Crimes_Weather.HIST_DIM_Weapon_Used
    (weapon_code, weapon_description, start_datetime, end_datetime)
    SELECT 
        i.weapon_code, 
        i.weapon_description, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Update_DIM_Weapon_Used
ON Crimes_Weather.DIM_Weapon_Used
AFTER UPDATE
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    -- Update the end date of the current record in the history table
    UPDATE History_Crimes_Weather.HIST_DIM_Weapon_Used
    SET end_datetime = @currentDatetime
    FROM History_Crimes_Weather.HIST_DIM_Weapon_Used h
    INNER JOIN deleted d ON h.weapon_code = d.weapon_code
    WHERE h.end_datetime IS NULL;

    -- Insert the updated record as a new entry in the history table
    INSERT INTO History_Crimes_Weather.HIST_DIM_Weapon_Used
    (weapon_code, weapon_description, start_datetime, end_datetime)
    SELECT 
        i.weapon_code, 
        i.weapon_description, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Insert_DIM_Descent
ON Crimes_Weather.DIM_Descent
AFTER INSERT
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    INSERT INTO History_Crimes_Weather.HIST_DIM_Descent
    (descent, descent_description, start_datetime, end_datetime)
    SELECT 
        i.descent, 
        i.descent_description, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Update_DIM_Descent
ON Crimes_Weather.DIM_Descent
AFTER UPDATE
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    -- Update the end date of the current record in the history table
    UPDATE History_Crimes_Weather.HIST_DIM_Descent
    SET end_datetime = @currentDatetime
    FROM History_Crimes_Weather.HIST_DIM_Descent h
    INNER JOIN deleted d ON h.descent = d.descent
    WHERE h.end_datetime IS NULL;

    -- Insert the updated record as a new entry in the history table
    INSERT INTO History_Crimes_Weather.HIST_DIM_Descent
    (descent, descent_description, start_datetime, end_datetime)
    SELECT 
        i.descent, 
        i.descent_description, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Insert_DIM_Premise
ON Crimes_Weather.DIM_Premise
AFTER INSERT
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    INSERT INTO History_Crimes_Weather.HIST_DIM_Premise
    (premise_id, premise_description, start_datetime, end_datetime)
    SELECT 
        i.premise_id, 
        i.premise_description, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Update_DIM_Premise
ON Crimes_Weather.DIM_Premise
AFTER UPDATE
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    -- Update the end date of the current record in the history table
    UPDATE History_Crimes_Weather.HIST_DIM_Premise
    SET end_datetime = @currentDatetime
    FROM History_Crimes_Weather.HIST_DIM_Premise h
    INNER JOIN deleted d ON h.premise_id = d.premise_id
    WHERE h.end_datetime IS NULL;

    -- Insert the updated record as a new entry in the history table
    INSERT INTO History_Crimes_Weather.HIST_DIM_Premise
    (premise_id, premise_description, start_datetime, end_datetime)
    SELECT 
        i.premise_id, 
        i.premise_description, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Insert_DIM_Area
ON Crimes_Weather.DIM_Area
AFTER INSERT
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    INSERT INTO History_Crimes_Weather.HIST_DIM_Area
    (area_id, area_name, start_datetime, end_datetime)
    SELECT 
        i.area_id, 
        i.area_name, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Update_DIM_Area
ON Crimes_Weather.DIM_Area
AFTER UPDATE
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    -- Update the end date of the current record in the history table
    UPDATE History_Crimes_Weather.HIST_DIM_Area
    SET end_datetime = @currentDatetime
    FROM History_Crimes_Weather.HIST_DIM_Area h
    INNER JOIN deleted d ON h.area_id = d.area_id
    WHERE h.end_datetime IS NULL;

    -- Insert the updated record as a new entry in the history table
    INSERT INTO History_Crimes_Weather.HIST_DIM_Area
    (area_id, area_name, start_datetime, end_datetime)
    SELECT 
        i.area_id, 
        i.area_name, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;


CREATE TRIGGER trg_Insert_DIM_Weather_Condition
ON Crimes_Weather.DIM_Weather_Condition
AFTER INSERT
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    INSERT INTO History_Crimes_Weather.HIST_DIM_Weather_Condition
    (weather_condition_id, weather_condition, start_datetime, end_datetime)
    SELECT 
        i.weather_condition_id, 
        i.weather_condition, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Update_DIM_Weather_Condition
ON Crimes_Weather.DIM_Weather_Condition
AFTER UPDATE
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    -- Update the end date of the current record in the history table
    UPDATE History_Crimes_Weather.HIST_DIM_Weather_Condition
    SET end_datetime = @currentDatetime
    FROM History_Crimes_Weather.HIST_DIM_Weather_Condition h
    INNER JOIN deleted d ON h.weather_condition_id = d.weather_condition_id
    WHERE h.end_datetime IS NULL;

    -- Insert the updated record as a new entry in the history table
    INSERT INTO History_Crimes_Weather.HIST_DIM_Weather_Condition
    (weather_condition_id, weather_condition, start_datetime, end_datetime)
    SELECT 
        i.weather_condition_id, 
        i.weather_condition, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Insert_DIM_Sex
ON Crimes_Weather.DIM_Sex
AFTER INSERT
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    INSERT INTO History_Crimes_Weather.HIST_DIM_Sex
    (sex, sex_description, start_datetime, end_datetime)
    SELECT 
        i.sex, 
        i.sex_description, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Update_DIM_Sex
ON Crimes_Weather.DIM_Sex
AFTER UPDATE
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    -- Update the end date of the current record in the history table
    UPDATE History_Crimes_Weather.HIST_DIM_Sex
    SET end_datetime = @currentDatetime
    FROM History_Crimes_Weather.HIST_DIM_Sex h
    INNER JOIN deleted d ON h.sex = d.sex
    WHERE h.end_datetime IS NULL;

    -- Insert the updated record as a new entry in the history table
    INSERT INTO History_Crimes_Weather.HIST_DIM_Sex
    (sex, sex_description, start_datetime, end_datetime)
    SELECT 
        i.sex, 
        i.sex_description, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Insert_DIM_Sunrise_Sunset
ON Crimes_Weather.DIM_Sunrise_Sunset
AFTER INSERT
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    INSERT INTO History_Crimes_Weather.HIST_DIM_Sunrise_Sunset
    (id, name, start_datetime, end_datetime)
    SELECT 
        i.id, 
        i.name, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;

CREATE TRIGGER trg_Update_DIM_Sunrise_Sunset
ON Crimes_Weather.DIM_Sunrise_Sunset
AFTER UPDATE
AS
BEGIN
    DECLARE @currentDatetime DATETIME = GETDATE();

    -- Update the end date of the current record in the history table
    UPDATE History_Crimes_Weather.HIST_DIM_Sunrise_Sunset
    SET end_datetime = @currentDatetime
    FROM History_Crimes_Weather.HIST_DIM_Sunrise_Sunset h
    INNER JOIN deleted d ON h.id = d.id
    WHERE h.end_datetime IS NULL;

    -- Insert the updated record as a new entry in the history table
    INSERT INTO History_Crimes_Weather.HIST_DIM_Sunrise_Sunset
    (id, name, start_datetime, end_datetime)
    SELECT 
        i.id, 
        i.name, 
        @currentDatetime, 
        NULL
    FROM inserted i;
END;
