
USE [Lab_6]

-- create tables
CREATE TABLE [Client](
	[ClientID] [int] NOT NULL,
	[ClientName] [varchar](50) NOT NULL,
	[Address] [varchar](100) NULL,
	[Email] [varchar](30) NULL,
	[Phone] [nvarchar](20) NULL,
	[Business] [varchar](30) NOT NULL,
 CONSTRAINT [PK__Client__E67E1A0483F5B901] PRIMARY KEY 
(
	[ClientID] ASC
)
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Department](
	[DepartmentNo] [int] NOT NULL,
	[DepartmentName] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[DepartmentNo] ASC
)
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Employee](
	[EmployeeNo] [int] NOT NULL,
	[EmployeeName] [varchar](30) NOT NULL,
	[Job] [varchar](50) NULL,
	[Salary] [int] NULL,
	[DepartmentNo] [int] NULL,
 CONSTRAINT [PK__Employee__7AD0F1B6F1C42EB4] PRIMARY KEY 
(
	[EmployeeNo] ASC
)
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [EmployeeProjectTask](
	[EmployeeProjectTaskID] [int] NOT NULL,
	[ProjectID] [int] NOT NULL,
	[EmployeeNo] [int] NOT NULL,
	[StartDate] [date] NULL,
	[EndDate] [date] NULL,
	[Task] [varchar](100) NULL,
	[Status] [varchar](30) NULL,
 CONSTRAINT [PK_EmployeeProjectTask] PRIMARY KEY 
(
	[EmployeeProjectTaskID] ASC
)
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [Project](
	[ProjectID] [int] NOT NULL,
	[Description] [varchar](100) NULL,
	[StartDate] [date] NULL,
	[PlannedEndDate] [date] NULL,
	[ActualEndDate] [date] NULL,
	[Budget] [int] NULL,
	[ClientID] [int] NULL,
PRIMARY KEY 
(
	[ProjectID] ASC
)
) ON [PRIMARY]
GO

ALTER TABLE [Employee]  WITH CHECK ADD  CONSTRAINT [FK__Employee__Depart__2F10007B] FOREIGN KEY([DepartmentNo])
REFERENCES [Department] ([DepartmentNo])
GO
ALTER TABLE [Employee] CHECK CONSTRAINT [FK__Employee__Depart__2F10007B]
GO
ALTER TABLE [EmployeeProjectTask]  WITH CHECK ADD  CONSTRAINT [FK__EmployeeP__Emplo__32E0915F] FOREIGN KEY([EmployeeNo])
REFERENCES [Employee] ([EmployeeNo])
GO
ALTER TABLE [EmployeeProjectTask] CHECK CONSTRAINT [FK__EmployeeP__Emplo__32E0915F]
GO
ALTER TABLE [EmployeeProjectTask]  WITH CHECK ADD  CONSTRAINT [FK__EmployeeP__Proje__31EC6D26] FOREIGN KEY([ProjectID])
REFERENCES [Project] ([ProjectID])
GO
ALTER TABLE [EmployeeProjectTask] CHECK CONSTRAINT [FK__EmployeeP__Proje__31EC6D26]
GO
ALTER TABLE [Project]  WITH CHECK ADD  CONSTRAINT [FK__Project__ClientI__286302EC] FOREIGN KEY([ClientID])
REFERENCES [Client] ([ClientID])
GO
ALTER TABLE [Project] CHECK CONSTRAINT [FK__Project__ClientI__286302EC]
GO
ALTER TABLE [Employee]  WITH CHECK ADD  CONSTRAINT [CK__Employee__Salary__2E1BDC42] CHECK  (([Salary]>(1700)))
GO
ALTER TABLE [Employee] CHECK CONSTRAINT [CK__Employee__Salary__2E1BDC42]
GO
ALTER TABLE [Project]  WITH CHECK ADD  CONSTRAINT [Budget] CHECK  (([Budget]>(0)))
GO
ALTER TABLE [Project] CHECK CONSTRAINT [Budget]
GO
ALTER TABLE [Project]  WITH CHECK ADD  CONSTRAINT [PlannedEndDate] CHECK  (([ActualEndDate]>[PlannedEndDate]))
GO
ALTER TABLE [Project] CHECK CONSTRAINT [PlannedEndDate]
GO

--insert data
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (1, N'Acme Corp', N'123 Acme Street, Acme City, AC', N'contact@acmecorp.com', N'1234567890', N'Manufacturing')
GO
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (2, N'Big Retailer', N'456 Market Road, Commerce City, CC', N'info@bigretailer.com', N'2345678901', N'Retail')
GO
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (3, N'Tech Solutions', N'789 Silicon Blvd, Tech Town, TT', N'hello@techsolutions.com', N'3456789012', N'Information Technology')
GO
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (4, N'Finance World', N'12 Wall Street, Money City, MC', N'fw@financeworld.com', N'4567890123', N'Finance')
GO
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (5, N'Creative Minds', N'34 Art Lane, Design District, DD', N'ideas@creativeminds.com', N'5678901234', N'Design')
GO
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (6, N'Global Logistics', N'56 Shipping Avenue, Transport Town, TT', N'support@globallogistics.com', N'6789012345', N'Logistics')
GO
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (7, N'Healthy Living', N'78 Wellness Way, Healthy City, HC', N'customers@healthyliving.com', N'7890123456', N'Healthcare')
GO
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (8, N'Educate Now', N'90 Knowledge Road, School City, SC', N'admissions@educatenow.com', N'8901234567', N'Education')
GO
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (9, N'Green Power', N'12 Solar Street, Green City, GC', N'greenpower@greenpower.com', N'9012345678', N'Renewable Energy')
GO
INSERT [Client] ([ClientID], [ClientName], [Address], [Email], [Phone], [Business]) VALUES (10, N'Sports Unlimited', N'34 Athletic Blvd, Sportstown, ST', N'info@sportsunlimited.com', N'1023456789', N'Sports')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (1, N'Human Resources')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (2, N'Finance')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (3, N'Sales')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (4, N'Marketing')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (5, N'IT')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (6, N'Design')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (7, N'Research and Development')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (8, N'Customer Support')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (9, N'Logistics')
GO
INSERT [Department] ([DepartmentNo], [DepartmentName]) VALUES (10, N'Operations')
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (1, N'Alice Johnson', N'HR Manager', 60000, 1)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (2, N'Bob Smith', N'Financial Analyst', 55000, 2)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (3, N'Charlie Brown', N'Sales Executive', 50000, 3)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (4, N'Diana Williams', N'Marketing Specialist', 52000, 4)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (5, N'Eva Green', N'Software Developer', 70000, 5)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (6, N'Frank Clark', N'Graphic Designer', 48000, 6)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (7, N'Grace Lee', N'Research Scientist', 65000, 7)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (8, N'Henry Davis', N'Customer Support Lead', 45000, 8)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (9, N'Ivy Adams', N'Logistics Coordinator', 42000, 9)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (10, N'Jack White', N'Operations Manager', 58000, 10)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (11, N'Kimberly Thomas', N'HR Specialist', 45000, 1)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (12, N'Liam Turner', N'Accountant', 49000, 2)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (13, N'Mason Taylor', N'Sales Representative', 47000, 3)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (14, N'Natalie Johnson', N'Marketing Coordinator', 43000, 4)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (15, N'Oliver Baker', N'IT Support Specialist', 46000, 5)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (16, N'Penelope Campbell', N'Graphic Designer', 42000, 6)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (17, N'Quentin James', N'Research Associate', 52000, 7)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (18, N'Rebecca Scott', N'Customer Support Agent', 41000, 8)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (19, N'Samuel Mitchell', N'Logistics Analyst', 48000, 9)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (20, N'Tiffany King', N'Operations Coordinator', 44000, 10)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (21, N'Ursula Wong', N'Recruiter', 47000, 1)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (22, N'Victor Harris', N'Financial Assistant', 41000, 2)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (23, N'Wendy Martin', N'Sales Analyst', 45000, 3)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (24, N'Xavier Thompson', N'Marketing Analyst', 48000, 4)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (25, N'Yolanda Hall', N'IT Project Manager', 67000, 5)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (26, N'Zachary Young', N'UI/UX Designer', 55000, 6)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (27, N'Ariana Rivera', N'Data Scientist', 63000, 7)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (28, N'Bradley Simmons', N'Customer Support Agent', 41000, 8)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (29, N'Cassandra Cooper', N'Logistics Specialist', 47000, 9)
GO
INSERT [Employee] ([EmployeeNo], [EmployeeName], [Job], [Salary], [DepartmentNo]) VALUES (30, N'Derek Murphy', N'Operations Analyst', 50000, 10)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (1, N'Product Design', CAST(N'2021-01-01' AS Date), CAST(N'2021-03-31' AS Date), CAST(N'2021-04-30' AS Date), 10000, 1)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (2, N'Inventory Management System', CAST(N'2021-02-15' AS Date), CAST(N'2021-05-30' AS Date), CAST(N'2021-06-10' AS Date), 20000, 2)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (3, N'Website Development', CAST(N'2021-04-01' AS Date), CAST(N'2021-07-15' AS Date), CAST(N'2021-08-15' AS Date), 15000, 3)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (4, N'Financial Analysis', CAST(N'2021-06-01' AS Date), CAST(N'2021-08-31' AS Date), CAST(N'2021-09-20' AS Date), 12000, 4)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (5, N'Branding and Logo Design', CAST(N'2021-08-15' AS Date), CAST(N'2021-10-15' AS Date), CAST(N'2021-11-10' AS Date), 8000, 5)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (6, N'Supply Chain Optimization', CAST(N'2021-09-01' AS Date), CAST(N'2021-12-31' AS Date), CAST(N'2022-01-15' AS Date), 25000, 6)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (7, N'Wellness Program Development', CAST(N'2021-11-01' AS Date), CAST(N'2022-02-28' AS Date), CAST(N'2022-03-28' AS Date), 18000, 7)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (8, N'E-Learning Platform', CAST(N'2022-01-15' AS Date), CAST(N'2022-04-30' AS Date), CAST(N'2022-05-30' AS Date), 30000, 8)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (9, N'Solar Energy Installation', CAST(N'2022-03-01' AS Date), CAST(N'2022-06-30' AS Date), CAST(N'2022-07-10' AS Date), 35000, 9)
GO
INSERT [Project] ([ProjectID], [Description], [StartDate], [PlannedEndDate], [ActualEndDate], [Budget], [ClientID]) VALUES (10, N'Sports Facility Upgrade', CAST(N'2022-05-01' AS Date), CAST(N'2022-08-15' AS Date), CAST(N'2022-09-01' AS Date), 40000, 10)
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (1, 1, 6, CAST(N'2021-01-10' AS Date), CAST(N'2021-02-28' AS Date), N'Initial Design', N'Completed')
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (2, 1, 6, CAST(N'2021-03-01' AS Date), CAST(N'2021-03-29' AS Date), N'Final Design', N'Completed')
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (3, 2, 5, CAST(N'2021-03-01' AS Date), CAST(N'2021-04-30' AS Date), N'Develop Backend', N'Completed')
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (4, 2, 5, CAST(N'2021-05-01' AS Date), CAST(N'2021-06-09' AS Date), N'Develop Frontend', N'Completed')
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (5, 3, 4, CAST(N'2021-05-15' AS Date), CAST(N'2021-07-14' AS Date), N'Digital Marketing', N'Completed')
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (6, 4, 2, CAST(N'2021-07-01' AS Date), CAST(N'2021-08-19' AS Date), N'Financial Analysis', N'Completed')
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (7, 5, 6, CAST(N'2021-09-01' AS Date), CAST(N'2021-10-09' AS Date), N'Logo Design', N'Completed')
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (8, 6, 9, CAST(N'2021-11-01' AS Date), CAST(N'2022-01-14' AS Date), N'Analyze Supply Chain', N'Completed')
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (9, 7, 7, CAST(N'2022-01-15' AS Date), CAST(N'2022-02-27' AS Date), N'Wellness Research', N'Completed')
GO
INSERT [EmployeeProjectTask] ([EmployeeProjectTaskID], [ProjectID], [EmployeeNo], [StartDate], [EndDate], [Task], [Status]) VALUES (10, 8, 5, CAST(N'2022-03-01' AS Date), CAST(N'2022-04-29' AS Date), N'E-Learning Platform Development', N'Completed')
GO


