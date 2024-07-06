-- TASK 7
-- Step 1: Create helper table for months
CREATE TABLE  Shepel_Molenda.Helper_Month (
    MonthNumber INT PRIMARY KEY,
    MonthName VARCHAR(20) 
);

-- Populate the helper table with month names
INSERT INTO Shepel_Molenda.Helper_Month (MonthNumber, MonthName)
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

CREATE TABLE  Shepel_Molenda.Helper_Weekday (
    WeekdayNumber INT ,
    WeekdayName VARCHAR(20)
);

INSERT INTO Shepel_Molenda.Helper_Weekday (WeekdayNumber, WeekdayName)
VALUES
    (1, 'Sunday'),
    (2, 'Monday'),
    (3, 'Tuesday'),
    (4, 'Wednesday'),
    (5, 'Thursday'),
    (6, 'Friday'),
    (7, 'Saturday');

CREATE TABLE Shepel_Molenda.DIM_TIME (
    PK_TIME INT PRIMARY KEY,
    Year INT,
    Month VARCHAR(20),
    Weekday VARCHAR(20),
    DayOfMonth INT
);

INSERT INTO Shepel_Molenda.DIM_TIME (PK_TIME, Year, Month, Weekday, DayOfMonth)
SELECT DISTINCT PK_TIME, Year, MonthNumber, WeekdayNumber, DayOfMonth FROM (
    SELECT 
        FS.OrderDate AS PK_TIME,
        CAST(LEFT(CONVERT(VARCHAR, FS.OrderDate), 4) AS INT) AS Year,
        HM.MonthName AS MonthName,
        HW.WeekdayName AS Week,
        CAST(RIGHT(CONVERT(VARCHAR, FS.OrderDate), 2) AS INT) AS DayOfMonth
    FROM 
        Shepel_Molenda.FACT_SALES AS FS
    JOIN 
        Shepel_Molenda.Helper_Month HM 
        ON CONVERT(INT, RIGHT(LEFT(CONVERT(VARCHAR, FS.OrderDate), 6), 2)) = HM.MonthNumber
    JOIN 
        Shepel_Molenda.Helper_Weekday HW
        ON DATEPART(WEEKDAY, CONVERT(DATETIME, CONVERT(VARCHAR, FS.OrderDate), 112)) = HW.WeekdayNumber

    UNION

    SELECT 
        FS.ShipDate AS PK_TIME,
        CAST(LEFT(CONVERT(VARCHAR, FS.ShipDate), 4) AS INT) AS Year,
        HM.MonthNumber AS MonthNumber,
        HW.WeekdayNumber AS WeekdayNumber,
        CAST(RIGHT(CONVERT(VARCHAR, FS.ShipDate), 2) AS INT) AS DayOfMonth

    FROM 
        Shepel_Molenda.FACT_SALES AS FS
    JOIN 
        Shepel_Molenda.Helper_Month HM 
        ON CONVERT(INT, RIGHT(LEFT(CONVERT(VARCHAR, FS.ShipDate), 6), 2)) = HM.MonthNumber
    JOIN 
        Shepel_Molenda.Helper_Weekday HW
        ON DATEPART(WEEKDAY, CONVERT(DATETIME, CONVERT(VARCHAR, FS.ShipDate), 112)) = HW.WeekdayNumber
) AS T;


ALTER TABLE Shepel_Molenda.FACT_SALES
ADD CONSTRAINT FK_OrderDate
FOREIGN KEY (OrderDate)
REFERENCES Shepel_Molenda.DIM_TIME(PK_TIME);


ALTER TABLE Shepel_Molenda.FACT_SALES
ADD CONSTRAINT FK_ShipDate
FOREIGN KEY (ShipDate)
REFERENCES Shepel_Molenda.DIM_TIME(PK_TIME);




-- Update Color column
UPDATE Shepel_Molenda.DIM_PRODUCT
SET Color = 'Unknown'
WHERE Color IS NULL;

-- Update SubCategoryName column
UPDATE Shepel_Molenda.DIM_PRODUCT
SET SubCategoryName = 'Unknown'
WHERE SubCategoryName IS NULL;

UPDATE Shepel_Molenda.DIM_PRODUCT
SET CategoryName = 'Unknown'
WHERE CategoryName IS NULL;


-- UPDATE SALESPERSON TABLE
SELECT * FROM Shepel_Molenda.DIM_SALESPERSON
-- Update CountryRegionCode column
UPDATE Shepel_Molenda.DIM_SALESPERSON
SET CountryRegionCode = '000'
WHERE CountryRegionCode IS NULL;

UPDATE Shepel_Molenda.DIM_SALESPERSON
SET Title = ''
WHERE Title IS NULL;

-- Update GroupName column
UPDATE Shepel_Molenda.DIM_SALESPERSON
SET GroupName = 'Unknown'
WHERE GroupName IS NULL;