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
ON SSP.TerritoryID = SST.TerritoryID;


-- INSERT INTO FACT_SALES
-- STILL NEED TO VERIFY FOR SalesPersonID
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