CREATE TABLE Shepel_Molenda.DIM_CUSTOMER (
    CustomerID INT PRIMARY KEY,
    FirstName NVARCHAR(50),
    LastName NVARCHAR(50),
    TerritoryName NVARCHAR(50),
    CountryRegionCode NVARCHAR(3),
    GroupName NVARCHAR(50)
);

CREATE TABLE Shepel_Molenda.DIM_PRODUCT (
    ProductID INT PRIMARY KEY,
    Name NVARCHAR(50),
    ListPrice MONEY,
    Color NVARCHAR(15),
    SubCategoryName NVARCHAR(50),
    CategoryName NVARCHAR(50)
);

CREATE TABLE Shepel_Molenda.DIM_SALESPERSON (
    SalesPersonID INT PRIMARY KEY,
    FirstName NVARCHAR(50),
    LastName NVARCHAR(50),
    Title NVARCHAR(8), -- A courtesy title. For example, Mr. or Ms.
    Gender NCHAR(1),
    CountryRegionCode NVARCHAR(3),
    GroupName NVARCHAR(50), -- Name of the group to which the department belongs
    Age INT
);

-- Added SalesOrderDetailID to uniquely identify record
CREATE TABLE Shepel_Molenda.FACT_SALES (
    SalesOrderID INT,
    SalesOrderDetailID INT,
    ProductID INT,
    CustomerID INT,
    SalesPersonID INT,
    OrderDate INT,
    ShipDate INT,
    OrderQty SMALLINT,
    UnitPrice MONEY,
    UnitPriceDiscount MONEY,
    LineTotal NUMERIC(38, 6),
    PRIMARY KEY (SalesOrderID, SalesOrderDetailID)
);

WITH test_cte AS (

SELECT DISTINCT PK_TIME,  Year, MonthNumber, WeekdayNumber, DayOfMonth FROM (
    SELECT
        FS.OrderDate AS PK_TIME,
        CAST(LEFT(CONVERT(VARCHAR, FS.OrderDate), 4) AS INT) AS Year,
        HM.MonthName AS MonthNumber,
        HW.WeekdayName AS WeekdayNumber,
        CONVERT(INT, RIGHT(CONVERT(VARCHAR, FS.OrderDate), 2)) AS DayOfMonth
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
        ShipDate AS PK_TIME,
        CAST(LEFT(CONVERT(VARCHAR, FS.ShipDate), 4) AS INT) AS Year,
        HM.MonthName AS MonthNumber,
        HW.WeekdayName AS WeekdayNumber,
        CONVERT(INT, RIGHT(CONVERT(VARCHAR, FS.ShipDate), 2)) AS DayOfMonth
    FROM
        Shepel_Molenda.FACT_SALES AS FS
    JOIN
        Shepel_Molenda.Helper_Month HM
        ON CONVERT(INT, RIGHT(LEFT(CONVERT(VARCHAR, FS.ShipDate), 6), 2)) = HM.MonthNumber
    JOIN
        Shepel_Molenda.Helper_Weekday HW
        ON DATEPART(WEEKDAY, CONVERT(DATETIME, CONVERT(VARCHAR, FS.ShipDate), 112)) = HW.WeekdayNumber
) AS T
)
INSERT INTO Shepel_Molenda.DIM_TIME (PK_TIME, Year, Month, Weekday, DayOfMonth)
SELECT PK_TIME, MAX(Year), MAX(MonthNumber), MAX(WeekdayNumber), MAX(DayOfMonth) FROM test_cte GROUP BY PK_TIME;
