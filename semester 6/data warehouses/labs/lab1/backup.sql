-- LAB 1 

-- PART 1

-- TASKS 1,2,3 are done

-- TASK 4 
-- List all tables in the AdventureWorks2022 database
USE AdventureWorks2022;
GO

SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_TYPE = 'BASE TABLE';

-- GET ALL SCHEMAS
/*
    MAIN SCHEMAS
        HumanResources
        Person
        Production
        Purchasing
        Sales
*/
SELECT schema_name
FROM information_schema.schemata;

-- GET SCHEMA NAME FOR PARTICULAR TABLE
SELECT TABLE_SCHEMA
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_NAME = 'Product';

-- GET COLUMN NAMES IN SELECTED TABLE
SELECT COLUMN_NAME, DATA_TYPE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'Product';

-- SELECT SAMPLE DATA FROM SELECTED TABLE
SELECT TOP 10 *
FROM Production.Product;

-- TASK 5

-- a,b
SELECT Name,ListPrice FROM Production.Product
WHERE ListPrice > 2500;

-- c Just click on the top icon on the right side

-- PART 2
-- TASK 1
    -- a
        -- I Where the detailed information about orders is stored? (SalesOrderDetail,SalesOrderHeader)
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema = 'Sales';

            -- Sales order detail table
            SELECT TOP 10 * FROM Sales.SalesOrderDetail;
        -- II Are there different types of orders ? YES : Work, Purchase Order, Sales order
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema = 'Purchasing';

            SELECT TOP 10 * FROM Purchasing.PurchaseOrderDetail;

            SELECT table_name
            FROM information_schema.tables
            WHERE LOWER(table_name) LIKE '%order%';
        -- III Are there different statuses of orders? -- YES, there are : 1,3,4
            
            -- STATUSES
            SELECT TOP 10 *
            FROM Purchasing.PurchaseOrderHeader;
        
        -- IV Which numerical data can be used to measure the performance of an order (TotalDue) (Order Date, Due Date, and Ship Date)
            SELECT TOP 10 * FROM Sales.SalesOrderHeader;

    -- B
        -- I Information main tables about products 
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema = 'Production';

            SELECT table_name
            FROM information_schema.tables
            WHERE LOWER(table_name) LIKE '%product%';
            -- product, Product category,Product Description etc.
        -- II Are product organized in some manner? YES ( bikes,components,clothing,accessories)
            SELECT TOP 10 * FROM Production.ProductCategory;

        -- III what additional information is available ( \product ProductCostHistory, ProductPhoto, ProductInventory etc.)
            SELECT table_name
            FROM information_schema.tables
            WHERE LOWER(table_name) LIKE '%product%';
    -- C (Customer, Sales.CreditCard,Person.Person,Person.Address) 
        -- I 
            SELECT table_name
            FROM information_schema.tables
            WHERE LOWER(table_name) LIKE '%customer%';

            -- person types
            SELECT DISTINCT [PersonType] FROM Person.Person;

            SELECT TOP 10 * FROM Sales.vIndividualCustomer;

            SELECT TOP 10 * FROM Sales.vSalesPerson;
    -- D Employees handling orders
            SELECT
                TABLE_SCHEMA,
                TABLE_NAME, 
                COLUMN_NAME,
                DATA_TYPE
            FROM 
                INFORMATION_SCHEMA.COLUMNS
            WHERE 
                LOWER(COLUMN_NAME) LIKE '%employee%';

            SELECT TOP 10 * FROM Purchasing.PurchaseOrderHeader;
    -- E sales location
             SELECT
                TABLE_SCHEMA,
                TABLE_NAME,
                COLUMN_NAME,
                DATA_TYPE
            FROM 
                INFORMATION_SCHEMA.COLUMNS
            WHERE 
                LOWER(COLUMN_NAME) LIKE '%location%';

            SELECT TOP 10 * FROM Sales.SalesOrderHeader -- TerritoryID

            SELECT TOP 10 * FROM Sales.SalesTerritory; -- Sales Territory

    -- f IN SALES SCHEMA. ALL RELATED INFORMATION RELATED TO SALES CAN BE FOUND HERE

-- TASK 2
    
    -- a
    SELECT TOP 10 * FROM Sales.SalesOrderHeader;

    SELECT SUM(TotalDue) 'GLOBAL SALES ORDER' FROM Sales.SalesOrderHeader;

    -- b 
    SELECT 
        SUM(SubTotal) 'GLOBAL SALES AMOUNT'
    FROM 
        Sales.SalesOrderHeader;

    -- c
        SELECT 
            SUM(SOD.LineTotal) AS GlobalSalesAmount,
            SUM(SOD.OrderQty) AS TotalItemsSold
        FROM 
            Sales.SalesOrderHeader AS SOH 
        JOIN 
            Sales.SalesOrderDetail AS SOD ON SOH.SalesOrderID = SOD.SalesOrderID;
    -- d
        SELECT 
            YEAR(OrderDate) AS SalesYear,
            SUM(TotalDue) AS AnnualSalesAmount
        FROM 
            Sales.SalesOrderHeader
        GROUP BY 
            YEAR(OrderDate)
        ORDER BY 
            SalesYear;
    -- e
        SELECT 
            SUM((SOD.UnitPrice - P.StandardCost) * SOD.OrderQty) AS GlobalProfit
        FROM 
            Sales.SalesOrderHeader AS SOH
        JOIN 
            Sales.SalesOrderDetail AS SOD ON SOH.SalesOrderID = SOD.SalesOrderID
        JOIN 
            Production.Product AS P ON SOD.ProductID = P.ProductID;


