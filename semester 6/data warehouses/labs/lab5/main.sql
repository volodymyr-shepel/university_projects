-- CREATE SCHEMA Shepel_Molenda;

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
    SalesOrderDetailID INT PRIMARY KEY,
	ProductID INT,
    CustomerID INT,
    SalesPersonID INT,
    OrderDate INT,
    ShipDate INT,
    OrderQty SMALLINT,
    UnitPrice MONEY,
    UnitPriceDiscount MONEY,
    LineTotal NUMERIC(38, 6)
);


-- TASK 3
-- insert records into DIM_CUSTOMER table
INSERT INTO Shepel_Molenda.DIM_CUSTOMER (
	CustomerID, 
	FirstName,
	LastName,
	TerritoryName,
	CountryRegionCode,
	GroupName)
SELECT 
    SC.CustomerID AS CustomerID,
    PP.FirstName AS FirstName,
    PP.LastName AS LastName,
    SST.Name AS TerritoryName,
    SST.CountryRegionCode AS CountryRegionCode,
    SST."Group" AS GroupName
FROM Sales.Customer AS SC
LEFT JOIN Person.Person AS PP ON SC.PersonID = PP.BusinessEntityID
LEFT JOIN Sales.SalesTerritory AS SST ON SC.TerritoryID = SST.TerritoryID;


-- INSERT INTO DIM_PRODUCT
INSERT INTO Shepel_Molenda.DIM_PRODUCT (
    ProductID,
    Name,
    ListPrice,
    Color,
    SubCategoryName,
    CategoryName
)
SELECT 
    PP.ProductID AS ProductID,
    PP.Name AS Name,
    PP.ListPrice AS ListPrice,
    PP.Color AS Color,
    PPS.Name AS SubCategoryName,
    PPC.Name AS CategoryName
FROM Production.Product AS PP
LEFT JOIN Production.ProductSubcategory AS PPS ON PP.ProductSubcategoryID = PPS.ProductSubcategoryID
LEFT JOIN Production.ProductCategory AS PPC ON PPS.ProductCategoryID = PPC.ProductCategoryID;

-- INSERT INTO DIM_SALESPERSON
INSERT INTO Shepel_Molenda.DIM_SALESPERSON (SalesPersonID, FirstName, LastName, Title, Gender, CountryRegionCode, GroupName, Age)
SELECT 
	SSP.BusinessEntityID AS SalesPersonID,
	PP.FirstName AS FirstName,
	PP.LastName AS LastName,
	PP.Title AS Title,
	HRE.Gender AS Gender,
	SST.CountryRegionCode AS CountryRegionCode,
	SST."Group" AS GroupName,
	CASE 
        WHEN MONTH(GETDATE()) > MONTH(HRE.BirthDate) OR MONTH(GETDATE()) = MONTH(HRE.BirthDate) AND DAY(GETDATE()) >= DAY(HRE.BirthDate) THEN DATEDIFF(year, HRE.BirthDate, GETDATE()) 
        ELSE DATEDIFF(year, HRE.BirthDate, GETDATE()) - 1 
    END AS Age
	
FROM Sales.SalesPerson AS SSP
LEFT JOIN Person.Person AS PP
ON SSP.BusinessEntityID = PP.BusinessEntityID
LEFT JOIN HumanResources.Employee AS HRE
ON SSP.BusinessEntityID = HRE.BusinessEntityID
LEFT JOIN Sales.SalesTerritory AS SST
ON SSP.TerritoryID = SST.TerritoryID


-- INSERT INTO FACT_SALES
-- STILL NEED TO VERIFY FOR SalesPersonID
INSERT INTO Shepel_Molenda.FACT_SALES (
    SalesOrderDetailID,
    ProductID,
    CustomerID,
    SalesPersonID,
    OrderDate,
    ShipDate,
    OrderQty,
    UnitPrice,
    UnitPriceDiscount,
    LineTotal
)
SELECT
    SOD.SalesOrderDetailID,
    SOD.ProductID,
    SOH.CustomerID,
    SOH.SalesPersonID,
    CONVERT(INT, CONVERT(VARCHAR(4), YEAR(SOH.OrderDate)) +
                RIGHT('00' + CONVERT(VARCHAR(2), MONTH(SOH.OrderDate)), 2) +
                RIGHT('00' + CONVERT(VARCHAR(2), DAY(SOH.OrderDate)), 2)) AS EncodedOrderDate,
    CONVERT(INT, CONVERT(VARCHAR(4), YEAR(SOH.ShipDate)) +
                RIGHT('00' + CONVERT(VARCHAR(2), MONTH(SOH.ShipDate)), 2) +
                RIGHT('00' + CONVERT(VARCHAR(2), DAY(SOH.ShipDate)), 2)) AS EncodedShipDate,
    SOD.OrderQty,
    SOD.UnitPrice,
    SOD.UnitPriceDiscount,
    SOD.LineTotal
FROM 
    Sales.SalesOrderHeader AS SOH
INNER JOIN 
    Sales.SalesOrderDetail AS SOD ON SOH.SalesOrderID = SOD.SalesOrderID;


-- REFERENTIAL CONSTRAINTS TASK 4

-- Define referential constraints for FACT_SALES table
ALTER TABLE Shepel_Molenda.FACT_SALES
ADD CONSTRAINT FK_ProductID
    FOREIGN KEY (ProductID)
    REFERENCES Shepel_Molenda.DIM_PRODUCT (ProductID);

ALTER TABLE Shepel_Molenda.FACT_SALES
ADD CONSTRAINT FK_CustomerID
    FOREIGN KEY (CustomerID)
    REFERENCES Shepel_Molenda.DIM_CUSTOMER (CustomerID);

ALTER TABLE Shepel_Molenda.FACT_SALES
ADD CONSTRAINT FK_SalesPersonID
    FOREIGN KEY (SalesPersonID)
    REFERENCES Shepel_Molenda.DIM_SALESPERSON (SalesPersonID);

-- CHECK

SELECT * FROM Shepel_Molenda.DIM_SALESPERSON
SELECT * FROM Shepel_Molenda.DIM_PRODUCT
SELECT * FROM Shepel_Molenda.DIM_CUSTOMER
SELECT TOP 5 * FROM Shepel_Molenda.FACT_SALES


-- Correct case (which does not violate referential integrity constraints)
INSERT INTO Shepel_Molenda.FACT_SALES (
    SalesOrderDetailID,
    ProductID,
    CustomerID,
    SalesPersonID,
    OrderDate,
    ShipDate,
    OrderQty,
    UnitPrice,
    UnitPriceDiscount,
    LineTotal
)
VALUES
	(121318,999,16880,274,20241804,20241804,1,2024.994,0.00,2024.994) 

-- same primary key

INSERT INTO Shepel_Molenda.FACT_SALES (
    SalesOrderDetailID,
    ProductID,
    CustomerID,
    SalesPersonID,
    OrderDate,
    ShipDate,
    OrderQty,
    UnitPrice,
    UnitPriceDiscount,
    LineTotal
)
VALUES
	(121318,999,16880,274,20241804,20241804,1,2024.994,0.00,2024.994) 

-- Product ID which is not present in DIM_PRODUCT Table 
-- The INSERT statement conflicted with the FOREIGN KEY constraint "FK_ProductID". The conflict occurred in database "AdventureWorks2022",
-- table "Shepel_Molenda.DIM_PRODUCT", column 'ProductID'.
INSERT INTO Shepel_Molenda.FACT_SALES (
    SalesOrderDetailID,
    ProductID,
    CustomerID,
    SalesPersonID,
    OrderDate,
    ShipDate,
    OrderQty,
    UnitPrice,
    UnitPriceDiscount,
    LineTotal
)
VALUES
	(121319,1000,16880,274,20241804,20241804,1,2024.994,0.00,2024.994) 


DELETE FROM Shepel_Molenda.FACT_SALES WHERE SalesOrderDetailID = 121318


-- TASK 5 SCRIPT TO DROP TABLES IN SCHEMA

SELECT * FROM INFORMATION_SCHEMA.TABLES

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Shepel_Molenda' AND TABLE_NAME = 'FACT_SALES')
    DROP TABLE Shepel_Molenda.FACT_SALES;

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Shepel_Molenda' AND TABLE_NAME = 'DIM_SALESPERSON')
    DROP TABLE Shepel_Molenda.DIM_SALESPERSON;

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Shepel_Molenda' AND TABLE_NAME = 'DIM_PRODUCT')
    DROP TABLE Shepel_Molenda.DIM_PRODUCT;

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Shepel_Molenda' AND TABLE_NAME = 'DIM_CUSTOMER')
    DROP TABLE Shepel_Molenda.DIM_CUSTOMER;


-- TASK 7
-- Step 1: Create helper table for months
CREATE TABLE  Shepel_Molenda.Helper_Month (
    MonthNumber INT,
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
    WeekdayNumber INT,
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
        HM.MonthNumber AS MonthNumber,
        HW.WeekdayNumber AS WeekdayNumber,
        CAST(RIGHT(LEFT(CONVERT(VARCHAR, FS.OrderDate), 6), 2) AS INT) AS DayOfMonth
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
        HM.MonthNumber AS MonthNumber,
        HW.WeekdayNumber AS WeekdayNumber,
        CAST(RIGHT(LEFT(CONVERT(VARCHAR, FS.ShipDate), 6), 2) AS INT) AS DayOfMonth
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

-- check
SELECT TOP 5 * 
FROM Shepel_Molenda.FACT_SALES AS FS
INNER JOIN Shepel_Molenda.DIM_TIME AS DT_OrderDate
    ON FS.OrderDate = DT_OrderDate.PK_TIME
INNER JOIN Shepel_Molenda.DIM_TIME AS DT_ShipDate
    ON FS.ShipDate = DT_ShipDate.PK_TIME;

-- TASK 7.2

SELECT * FROM Shepel_Molenda.DIM_PRODUCT

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

-- Update GroupName column
UPDATE Shepel_Molenda.DIM_SALESPERSON
SET GroupName = 'Unknown'
WHERE GroupName IS NULL;