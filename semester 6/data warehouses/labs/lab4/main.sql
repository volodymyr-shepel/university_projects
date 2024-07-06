
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
-- Total execution time: 00:00:00.530
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
-- Total execution time: 00:00:00.650
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


-- CTE
-- Total execution time: 00:00:00.315
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


-- The solution employing windowed functions is considered less efficient due to the complexities associated with partitioning and distinct operations outlined in the task description. 
-- Therefore, it is suggested that the preferable solutions utilize either the GROUP BY clause or Common Table Expressions (CTEs). Both approaches offer satisfactory execution times 
-- and improve code readability.

-- TASK 2A


-- Base query
WITH Base_Query AS (
    SELECT 
    PP.ProductID AS Product_ID,
    PP.ProductSubcategoryID AS Subcategory_ID,
    PPS.ProductCategoryID AS Product_Category,
    PPS.Name AS Subcategory_Name,
    PPC.Name AS Category_Name,
    PP.Name AS Product_Name,
    PP.Color AS Product_Color,
    PP.ListPrice AS Product_List_Price,
    ROUND(AVG(PP.ListPrice) OVER(), 2) AS Avg_List_Price_All_Products
FROM Production.Product AS PP
INNER JOIN Production.ProductSubcategory AS PPS
    ON PP.ProductSubcategoryID = PPS.ProductSubcategoryID
INNER JOIN Production.ProductCategory AS PPC
    ON PPS.ProductCategoryID = PPC.ProductCategoryID
) 
SELECT BQ.Product_Color,BQ.Subcategory_Name
FROM Base_Query AS BQ


-- TASK 2A.2a
WITH Base_Query AS (
    SELECT 
    PP.ProductID AS Product_ID,
    PP.ProductSubcategoryID AS Subcategory_ID,
    PPS.ProductCategoryID AS Product_Category,
    PPS.Name AS Subcategory_Name,
    PPC.Name AS Category_Name,
    PP.Name AS Product_Name,
    PP.Color AS Product_Color,
    PP.ListPrice AS Product_List_Price,
    ROUND(AVG(PP.ListPrice) OVER(), 2) AS Avg_List_Price_All_Products
FROM Production.Product AS PP
INNER JOIN Production.ProductSubcategory AS PPS
    ON PP.ProductSubcategoryID = PPS.ProductSubcategoryID
INNER JOIN Production.ProductCategory AS PPC
    ON PPS.ProductCategoryID = PPC.ProductCategoryID
) ,
Aggregated_Query AS (
    SELECT 
        Subcategory_Name,
        Product_Color,
        AVG(Product_List_Price) AS Avg_Product_List_Price
    FROM Base_Query
    GROUP BY Subcategory_Name, Product_Color
)

SELECT 
    Subcategory_Name,
    [Black], [Blue], [Grey], [Missing], [Multi], [Red], [Silver], [Silver/Black], [White], [Yellow]
FROM Aggregated_Query
PIVOT
(
    AVG(Avg_Product_List_Price)
    FOR Product_Color IN ([Black], [Blue], [Grey], [Missing], [Multi], [Red], [Silver], [Silver/Black], [White], [Yellow])
) AS PivotTable;

-- TASK 2A.2b
WITH Base_Query AS (
    SELECT 
    PP.ProductID AS Product_ID,
    PP.ProductSubcategoryID AS Subcategory_ID,
    PPS.ProductCategoryID AS Product_Category,
    PPS.Name AS Subcategory_Name,
    PPC.Name AS Category_Name,
    PP.Name AS Product_Name,
    PP.Color AS Product_Color,
    PP.ListPrice AS Product_List_Price,
    ROUND(AVG(PP.ListPrice) OVER(), 2) AS Avg_List_Price_All_Products
FROM Production.Product AS PP
INNER JOIN Production.ProductSubcategory AS PPS
    ON PP.ProductSubcategoryID = PPS.ProductSubcategoryID
INNER JOIN Production.ProductCategory AS PPC
    ON PPS.ProductCategoryID = PPC.ProductCategoryID
) ,
Aggregated_Query AS (
    SELECT 
        Subcategory_Name,
        Product_Color,
        AVG(Product_List_Price) AS Avg_Product_List_Price
    FROM Base_Query
    WHERE Category_Name = 'Bikes'
    GROUP BY Subcategory_Name, Product_Color
)
SELECT 
    Subcategory_Name,
    [Black], [Blue], [Grey], [Missing], [Multi], [Red], [Silver], [Silver/Black], [White], [Yellow]
FROM Aggregated_Query
PIVOT
(
    AVG(Avg_Product_List_Price)
    FOR Product_Color IN ([Black], [Blue], [Grey], [Missing], [Multi], [Red], [Silver], [Silver/Black], [White], [Yellow])
) AS PivotTable;


-- TASK 2A.3

SELECT *
FROM (
    SELECT YEAR(SOH.OrderDate) AS Year,LEFT(DATENAME(MONTH, OrderDate), 3) AS Month_name,AVG(SOH.SubTotal) AS Average_Subtotal
FROM 
Sales.SalesOrderHeader AS SOH
GROUP BY YEAR(SOH.OrderDate),LEFT(DATENAME(MONTH, OrderDate), 3)
) AS Src
PIVOT(
    AVG(Src.Average_Subtotal)
    FOR Month_name IN ([Jan],[Feb],[Mar],[Apr],[May],[Jun],[Jul],[Aug],[Sep],[Oct],[Nov],[Dec])
) AS PivotTable;

-- TASK 2B

-- TASK 2B.1

SELECT 
    PP.ProductID AS Product_ID,
    PP.Name AS Product_name,
    PP.ListPrice AS Product_List_Price,
    CASE
        WHEN PP.ListPrice < 20.00 THEN 'Inexpensive'
        WHEN PP.ListPrice >= 20.00 AND PP.ListPrice < 75.00 THEN 'Regular'
        WHEN PP.ListPrice >= 75.00 AND PP.ListPrice < 750.00 THEN 'HIGH'
        WHEN PP.ListPrice >= 750.00 THEN 'Expensive'
    END AS Price_Category
FROM Production.Product AS PP

-- TASK 2B.2

SELECT DISTINCT PP.WeightUnitMeasureCode
FROM Production.Product AS PP

-- G,LB,NULL

SELECT 
    PP.Name
    ,PP.ProductID,
    PP.Weight AS Original_Weight,
    PP.WeightUnitMeasureCode AS Original_Units,
    CASE
        WHEN PP.WeightUnitMeasureCode = 'G' THEN PP.Weight / 1000
        WHEN PP.WeightUnitMeasureCode = 'LB' THEN PP.Weight * 0.453592
        ELSE -1
    END AS Product_Weight_kg
FROM Production.Product AS PP

-- TASL 2B.3


SELECT 
    Customer_ID, 
    Customer_Full_Name,
    COALESCE([Accessories], 0) AS Accessories,
    COALESCE([Bikes], 0) AS Bikes,
    COALESCE([Clothing], 0) AS Clothing,
    COALESCE([Components], 0) AS Components
FROM (
    SELECT *
    FROM (
        SELECT DISTINCT
            SOH.CustomerID AS Customer_ID,
            PersonP.FirstName + ' ' + PersonP.LastName AS Customer_Full_Name, 
            PPC.Name AS Category_Name,
            CASE WHEN COUNT(SOH.CustomerID) OVER (PARTITION BY SOH.CustomerID, PPC.Name) > 0 THEN 1 ELSE 0 END AS Purchased
        FROM Sales.SalesOrderHeader AS SOH
        INNER JOIN Sales.SalesOrderDetail AS SOD ON SOH.SalesOrderID = SOD.SalesOrderID
        INNER JOIN Production.Product AS PP ON SOD.ProductID = PP.ProductID
        INNER JOIN Production.ProductSubcategory AS PPS ON PP.ProductSubcategoryID = PPS.ProductSubcategoryID
        INNER JOIN Production.ProductCategory AS PPC ON PPS.ProductCategoryID = PPC.ProductCategoryID
        INNER JOIN Person.Person AS PersonP ON SOH.CustomerID = PersonP.BusinessEntityID
    ) AS SourceTable
    PIVOT (
        MAX(Purchased)
        FOR Category_Name IN ([Accessories], [Bikes], [Clothing], [Components])
    ) AS PivotTable
) AS FinalPivot;

-- TASK 2B.4

-- Order Amount
SELECT 
    PP.Color AS Product_Color,
    SUM(CASE WHEN YEAR(SOH.OrderDate) = 2011 THEN SOD.OrderQty ELSE 0 END) AS Order_Amount_2011,
    SUM(CASE WHEN YEAR(SOH.OrderDate) = 2012 THEN SOD.OrderQty ELSE 0 END) AS Order_Amount_2012,
    SUM(CASE WHEN YEAR(SOH.OrderDate) = 2013 THEN SOD.OrderQty ELSE 0 END) AS Order_Amount_2013,
    SUM(CASE WHEN YEAR(SOH.OrderDate) = 2014 THEN SOD.OrderQty ELSE 0 END) AS Order_Amount_2014
FROM
    Sales.SalesOrderHeader AS SOH
INNER JOIN Sales.SalesOrderDetail AS SOD ON SOH.SalesOrderID = SOD.SalesOrderID
INNER JOIN Production.Product AS PP ON SOD.ProductID = PP.ProductID
WHERE 
    PP.Color IS NOT NULL
GROUP BY 
    PP.Color


-- Sales Amount

SELECT 
    PP.Color AS Product_Color,
    SUM(CASE WHEN YEAR(SOH.OrderDate) = 2011 THEN SOD.OrderQty * SOD.UnitPrice ELSE 0 END) AS Sales_Amount_2011,
    SUM(CASE WHEN YEAR(SOH.OrderDate) = 2012 THEN SOD.OrderQty * SOD.UnitPrice ELSE 0 END) AS Sales_Amount_2012,
    SUM(CASE WHEN YEAR(SOH.OrderDate) = 2013 THEN SOD.OrderQty * SOD.UnitPrice ELSE 0 END) AS Sales_Amount_2013,
    SUM(CASE WHEN YEAR(SOH.OrderDate) = 2014 THEN SOD.OrderQty * SOD.UnitPrice ELSE 0 END) AS Sales_Amount_2014
FROM
    Sales.SalesOrderHeader AS SOH
INNER JOIN Sales.SalesOrderDetail AS SOD ON SOH.SalesOrderID = SOD.SalesOrderID
INNER JOIN Production.Product AS PP ON SOD.ProductID = PP.ProductID
WHERE 
    PP.Color IS NOT NULL
GROUP BY 
    PP.Color


-- TASK 2C 

-- TASK 2C.1
SELECT 
    YEAR(OrderDate) AS SalesYear,
    DATEPART(QUARTER, OrderDate) AS SalesQuarter,
    SUM(TotalDue) AS TotalSalesAmount
FROM 
    Sales.SalesOrderHeader
GROUP BY 
    YEAR(OrderDate),
    DATEPART(QUARTER, OrderDate)
WITH ROLLUP;

-- grouping retunrs 1 for aggregated or 0 for not aggregated in the result set
SELECT 
    CASE WHEN GROUPING(p.Weight) = 1 THEN 'All Products' ELSE CAST(p.Weight AS VARCHAR(50)) END AS ProductWeight,
    SUM(sod.LineTotal) AS SalesAmount
FROM 
    Sales.SalesOrderDetail sod
LEFT JOIN 
    Production.Product AS p 
    ON sod.ProductID = p.ProductID
GROUP BY 
    p.Weight WITH ROLLUP;

-- TASK 3
SELECT 
    YEAR(so.OrderDate) AS SalesYear,
    MONTH(so.OrderDate) AS SalesMonth,
    DAY(so.OrderDate) AS SalesDay,
    p.Name AS ProductName,
    SUM(sod.LineTotal) AS SalesAmount
FROM 
    Sales.SalesOrderDetail sod
INNER JOIN 
    Sales.SalesOrderHeader so ON sod.SalesOrderID = so.SalesOrderID
INNER JOIN 
    Production.Product p ON sod.ProductID = p.ProductID
GROUP BY 
    YEAR(so.OrderDate),
    MONTH(so.OrderDate),
    DAY(so.OrderDate),
    p.Name
WITH ROLLUP;

-- TASK 4
SELECT 
    CASE
        WHEN GROUPING(PPS.Name) = 1 THEN 'ALL'
        WHEN GROUPING(PPS.Name) = 0 AND PPS.Name IS NULL THEN 'Missing'
        ELSE PPS.Name
    END AS Category_Name,
    CASE 
        WHEN GROUPING(p.Color) = 1 THEN 'ALL'
        WHEN GROUPING(p.Color) = 0 AND p.Color IS NULL THEN 'Missing'
        ELSE p.Color
    END AS Product_Color,
    AVG(sod.UnitPrice) AS AveragePrice
FROM 
    Production.Product p
JOIN 
    Sales.SalesOrderDetail sod ON p.ProductID = sod.ProductID
JOIN 
    Production.ProductSubcategory AS PPS
    ON p.ProductSubcategoryID = PPS.ProductSubcategoryID
GROUP BY 
    PPS.Name, p.Color WITH ROLLUP;


-- task 4
SELECT 
    p.ProductSubcategoryID,
    c.CustomerID,
    YEAR(so.OrderDate) AS SalesYear,
    SUM(sod.LineTotal) AS TotalSalesAmount
FROM 
    Sales.SalesOrderDetail sod
JOIN 
    Sales.SalesOrderHeader so ON sod.SalesOrderID = so.SalesOrderID
JOIN 
    Sales.Customer c ON so.CustomerID = c.CustomerID
JOIN 
    Production.Product p ON sod.ProductID = p.ProductID
JOIN 
    Production.ProductSubcategory ps ON p.ProductSubcategoryID = ps.ProductSubcategoryID
JOIN 
    Production.ProductCategory pc ON ps.ProductCategoryID = pc.ProductCategoryID
WHERE 
    pc.Name = 'Bikes'
GROUP BY 
    p.ProductSubcategoryID,
    c.CustomerID,
    YEAR(so.OrderDate);
