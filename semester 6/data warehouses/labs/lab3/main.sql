USE AdventureWorks2022;

-- TASK 1

/*
Prepare a report (using proper SQL queries) to assess the yearly performance of individual
sales representatives working in AdventureWorks company. The key metrics that we are
focused on are the total sales and number of orders made by employees. The report should
contain data as shown in Table 1.

Sales Person EmployeeID Year SubTotal NumberofOrders

1. Prepare the report without using windowed functions (OVER clause)
2. Prepare the report using windowed functions (OVER clause).
3. Prepare the report by using CTE, where first you aggregate the sales data to
establish yearly performance metrices, and only then attaching the details of the
sales person.
*/

-- Sales.SalesOrderHeader

-- without windowed
-- distinct is needed since for sincle salesOrderID in SOH i have mulitple entries in salesOrderDetail(each product entry in order)
SELECT MAX(CONCAT(PP.LastName, ', ', PP.FirstName)) AS Sales_Person,
       SOH.SalesPersonID AS Employee_ID,
       YEAR(SOH.OrderDate) AS YEAR,
       CONVERT(DECIMAL(10,2),SUM(SOD.LineTotal)) AS Sub_Total,
       COUNT(DISTINCT SOH.SalesOrderID) AS Number_Of_Orders  
FROM Sales.SalesOrderHeader AS SOH
INNER JOIN Sales.SalesOrderDetail AS SOD ON SOD.SalesOrderID = SOH.SalesOrderID
INNER JOIN HumanResources.Employee AS HRE ON SOH.SalesPersonID = HRE.BusinessEntityID
INNER JOIN Person.Person AS PP ON HRE.BusinessEntityID = PP.BusinessEntityID
GROUP BY YEAR(SOH.OrderDate), SOH.SalesPersonID;

-- with windowed TODO:
-- this is not fully correct(because of number of orders ) FIX IT
SELECT DISTINCT
    MAX(CONCAT(PP.LastName, ', ', PP.FirstName)) OVER(PARTITION BY SOH.SalesPersonID, YEAR(SOH.OrderDate)) AS Sales_Person,
    SOH.SalesPersonID AS Employee_ID,
    YEAR(SOH.OrderDate) AS YEAR,
    SUM(Total_Sales.total_sum) OVER(PARTITION BY SOH.SalesPersonID,YEAR(SOH.OrderDate)),
    COUNT(*) OVER(PARTITION BY SOH.SalesPersonID, YEAR(SOH.OrderDate))
FROM Sales.SalesOrderHeader AS SOH
INNER JOIN HumanResources.Employee AS HRE ON SOH.SalesPersonID = HRE.BusinessEntityID
INNER JOIN Person.Person AS PP ON HRE.BusinessEntityID = PP.BusinessEntityID
INNER JOIN(
    SELECT DISTINCT 
    s1.SalesOrderID AS SO_ID,
    SUM(LineTotal) OVER(PARTITION BY SalesOrderID) AS total_sum
    FROM Sales.SalesOrderDetail s1
) AS Total_Sales
ON Total_Sales.SO_ID = SOH.SalesOrderID
WHERE SOH.SalesPersonID = 274

-- CTE

WITH YearlySalesMetrics AS (
    SELECT 
        SOH.SalesPersonID AS Employee_ID,
        YEAR(SOH.OrderDate) AS Year,
        SUM(SOD.LineTotal) AS Total_Sales_Amount,
        COUNT(DISTINCT SOH.SalesOrderID) AS Number_Of_Orders
    FROM 
        Sales.SalesOrderHeader AS SOH
    INNER JOIN 
        Sales.SalesOrderDetail AS SOD ON SOD.SalesOrderID = SOH.SalesOrderID
    GROUP BY 
        YEAR(SOH.OrderDate), SOH.SalesPersonID
)

SELECT 
    CONCAT(PP.LastName, ', ', PP.FirstName) AS Sales_Person,
    YSM.Employee_ID,
    YSM.Year,
    CONVERT(DECIMAL(10,2), YSM.Total_Sales_Amount) AS Total_Sales_Amount,
    YSM.Number_Of_Orders
FROM 
    YearlySalesMetrics AS YSM
INNER JOIN 
    HumanResources.Employee AS HRE ON YSM.Employee_ID = HRE.BusinessEntityID
INNER JOIN 
    Person.Person AS PP ON HRE.BusinessEntityID = PP.BusinessEntityID

-- TASK 2 (GROUPING SETS)
SELECT 
    SOH.SalesPersonID AS Employee_ID,
       YEAR(SOH.OrderDate) AS YEAR,
       CONVERT(DECIMAL(10,2),SUM(SOD.LineTotal)) AS Sub_Total,
       COUNT(DISTINCT SOH.SalesOrderID) AS Number_Of_Orders

FROM Sales.SalesOrderHeader AS SOH
INNER JOIN Sales.SalesOrderDetail AS SOD ON SOD.SalesOrderID = SOH.SalesOrderID
INNER JOIN HumanResources.Employee AS HRE ON SOH.SalesPersonID = HRE.BusinessEntityID
GROUP BY GROUPING SETS(YEAR(SOH.OrderDate),SOH.SalesPersonID, ())


-- TASK 3


SELECT MAX(PPS.Name),COUNT(*)
FROM Production.Product AS PP
INNER JOIN Production.ProductSubcategory AS PPS
ON PP.ProductSubcategoryID = PPS.ProductSubcategoryID
GROUP BY PP.ProductSubcategoryID


SELECT MAX(PPS.Name),COUNT(*)
FROM Production.Product AS PP
INNER JOIN Production.ProductSubcategory AS PPS
ON PP.ProductSubcategoryID = PPS.ProductSubcategoryID
WHERE PPS.Name LIKE '%Bike%'
GROUP BY PP.ProductSubcategoryID

SELECT TOP 10 * FROM Sales.Customer

SELECT COUNT(*),PP.ProductSubcategoryID
FROM Production.Product AS PP
GROUP BY PP.ProductSubcategoryID

SELECT 
    PP.ProductID AS Product_ID
    ,PP.Name AS Product_Name
    ,PPS.Name AS Product_Subcategory
    ,PPC.Name AS Product_Category
    ,PP.Color AS Product_Color
    ,PP.Weight AS Product_Weight
    ,PP.ListPrice AS Catalogue_Price
    ,SOD.UnitPrice AS Unit_Price
FROM Production.Product AS PP
INNER JOIN Production.ProductSubcategory AS PPS
ON PP.ProductSubcategoryID = PPS.ProductSubcategoryID
INNER JOIN Production.ProductCategory AS PPC
ON PPS.ProductCategoryID = PPC.ProductCategoryID
INNER JOIN Sales.SalesOrderDetail AS SOD
ON SOD.ProductID = PP.ProductID

SELECT TOP 10 * FROM Sales.SalesOrderDetail