USE [master]
GO
/****** Object:  Database [football_registration]    Script Date: 16-02-2022 12:34:31 ******/
CREATE DATABASE [football_registration]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'football_registration', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\football_registration.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'football_registration_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\football_registration_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [football_registration] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [football_registration].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [football_registration] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [football_registration] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [football_registration] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [football_registration] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [football_registration] SET ARITHABORT OFF 
GO
ALTER DATABASE [football_registration] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [football_registration] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [football_registration] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [football_registration] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [football_registration] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [football_registration] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [football_registration] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [football_registration] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [football_registration] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [football_registration] SET  DISABLE_BROKER 
GO
ALTER DATABASE [football_registration] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [football_registration] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [football_registration] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [football_registration] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [football_registration] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [football_registration] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [football_registration] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [football_registration] SET RECOVERY FULL 
GO
ALTER DATABASE [football_registration] SET  MULTI_USER 
GO
ALTER DATABASE [football_registration] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [football_registration] SET DB_CHAINING OFF 
GO
ALTER DATABASE [football_registration] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [football_registration] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [football_registration] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'football_registration', N'ON'
GO
ALTER DATABASE [football_registration] SET QUERY_STORE = OFF
GO
USE [football_registration]
GO
ALTER DATABASE SCOPED CONFIGURATION SET IDENTITY_CACHE = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [football_registration]
GO
/****** Object:  Table [dbo].[AGEGROUP]    Script Date: 16-02-2022 12:34:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AGEGROUP](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Range] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CITY]    Script Date: 16-02-2022 12:34:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CITY](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[NAME] [nvarchar](255) NOT NULL,
	[COUNTRYID] [int] NULL,
	[STATEID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[COUNTRY]    Script Date: 16-02-2022 12:34:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[COUNTRY](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[NAME] [nvarchar](255) NOT NULL,
	[CODE] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[POSITION]    Script Date: 16-02-2022 12:34:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[POSITION](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[NAME] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[STATE]    Script Date: 16-02-2022 12:34:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[STATE](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[NAME] [nvarchar](255) NOT NULL,
	[CODE] [nvarchar](255) NOT NULL,
	[COUNTRYID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TEAM]    Script Date: 16-02-2022 12:34:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TEAM](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[NAME] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[USER]    Script Date: 16-02-2022 12:34:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[USER](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[USERNAME] [nvarchar](255) NOT NULL,
	[FIRST_NAME] [nvarchar](255) NOT NULL,
	[LAST_NAME] [nvarchar](255) NULL,
	[EMAIL] [nvarchar](255) NOT NULL,
	[PHONE] [varchar](15) NOT NULL,
	[DAIL_CODE] [varchar](50) NOT NULL,
	[PINCODE] [varchar](16) NULL,
	[ADDRESS] [varchar](8000) NULL,
	[CREATED_ON] [datetime] NULL,
	[UPDATED_ON] [datetime] NULL,
	[COUNTRYID] [int] NULL,
	[STATEID] [int] NULL,
	[CITYID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[USERNAME] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[USER_REGISTRATION]    Script Date: 16-02-2022 12:34:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[USER_REGISTRATION](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[CREATED_ON] [datetime] NULL,
	[UPDATED_ON] [datetime] NULL,
	[USERID] [int] NOT NULL,
	[TEAMID] [int] NOT NULL,
	[POSITIONID] [nvarchar](50) NOT NULL,
	[AGEGROUPID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[USER] ADD  DEFAULT (getdate()) FOR [CREATED_ON]
GO
ALTER TABLE [dbo].[USER_REGISTRATION] ADD  DEFAULT (getdate()) FOR [CREATED_ON]
GO
ALTER TABLE [dbo].[CITY]  WITH CHECK ADD FOREIGN KEY([COUNTRYID])
REFERENCES [dbo].[COUNTRY] ([ID])
GO
ALTER TABLE [dbo].[CITY]  WITH CHECK ADD FOREIGN KEY([STATEID])
REFERENCES [dbo].[STATE] ([ID])
GO
ALTER TABLE [dbo].[STATE]  WITH CHECK ADD FOREIGN KEY([COUNTRYID])
REFERENCES [dbo].[COUNTRY] ([ID])
GO
ALTER TABLE [dbo].[USER]  WITH CHECK ADD FOREIGN KEY([CITYID])
REFERENCES [dbo].[CITY] ([ID])
GO
ALTER TABLE [dbo].[USER]  WITH CHECK ADD FOREIGN KEY([COUNTRYID])
REFERENCES [dbo].[COUNTRY] ([ID])
GO
ALTER TABLE [dbo].[USER]  WITH CHECK ADD FOREIGN KEY([STATEID])
REFERENCES [dbo].[STATE] ([ID])
GO
ALTER TABLE [dbo].[USER_REGISTRATION]  WITH CHECK ADD FOREIGN KEY([AGEGROUPID])
REFERENCES [dbo].[AGEGROUP] ([ID])
GO
ALTER TABLE [dbo].[USER_REGISTRATION]  WITH CHECK ADD FOREIGN KEY([TEAMID])
REFERENCES [dbo].[TEAM] ([ID])
GO
ALTER TABLE [dbo].[USER_REGISTRATION]  WITH CHECK ADD FOREIGN KEY([USERID])
REFERENCES [dbo].[USER] ([ID])
GO

/****** Add mendatory data to tables******/

INSERT INTO COUNTRY (NAME,CODE)
VALUES
    ('India', 'IN'),
    ('Norway', 'NO'),
    ('Denmark', 'DK')
GO    

INSERT INTO STATE(NAME,CODE, COUNTRYID)
VALUES
    ('Punjab', 'PB', 1),
    ('Rajasthan', 'RJ', 1),
    ('Oslo', '03', 2),
	('Vestfold', '07', 2),
	('North Denmark', '81', 3),
	('Zealand', '85', 3)
GO

INSERT INTO CITY(NAME, COUNTRYID, STATEID)
VALUES
    ('Amritsar', 1, 1),
    ('Nabha', 1, 1),
    ('Jaisalmer', 1, 2),
	('Jaipur', 1, 2),

	('Drammen', 2, 3),
    ('Fredrikstad', 2, 3),
    ('Notodden', 2, 4),
	('Porsgrunn', 2, 4),

	('Aras', 3, 5),
    ('Skagen', 3, 5),
    ('Faxe', 3, 6),
	('Greve', 3, 6)
GO

INSERT INTO TEAM(NAME)
VALUES
    ('CHELSEA'),
	('MANCHESTER UNITED'),
	('LIVERPOOL'),
	('BARCELONA')
GO

INSERT INTO POSITION(NAME)
VALUES
    ('GOAL KEEPER'),
	('OFFENSIVE'),
	('DEFENSE'),
	('RECEIVER')
GO

INSERT INTO AGEGROUP(Range)
VALUES
    ('16-19 years'),
	('20-23 years'),
	('24-27 years'),
	('28-32 years')
GO

/****** Object:  Trigger SET_UPDATEDON_USER ******/

CREATE TRIGGER SET_UPDATEDON_USER ON [USER]
AFTER INSERT, UPDATE 
AS
  UPDATE [USER]
  SET UPDATED_ON = GETDATE()
  FROM Inserted i
  WHERE [USER].ID = i.ID

GO

/****** Object:  Trigger SET_UPDATEON_USER_REGISTRATION ******/

CREATE TRIGGER SET_UPDATEON_USER_REGISTRATION ON [USER_REGISTRATION]
AFTER INSERT, UPDATE
AS
	UPDATE [USER_REGISTRATION]
	SET UPDATED_ON = GETDATE()
	FROM Inserted i
	WHERE [USER_REGISTRATION].ID = i.ID

GO

/****** Object:  StoredProcedure [dbo].[RegisterNewUser]    Script Date: 16-02-2022 12:34:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[RegisterNewUser] 
	@UserName nvarchar(255), 
	@FirstName nvarchar(255), 
	@LastName nvarchar(255), 
	@EMAIL nvarchar(255), 
	@PHONE nvarchar(255), 
	@DAIL_CODE nvarchar(50), 
	@PINCODE nvarchar(16), 
	@ADDRESS nvarchar(4000),
	@COUNTRYCODE nvarchar(255),
	@STATECODE nvarchar(255), 
	@CITYNAME nvarchar(255), 
	@AgeGroupId int,
	@TeamId int,
	@PositionIds nvarchar(50)
AS

BEGIN TRY

 IF NOT EXISTS(SELECT TOP 1 c.ID FROM COUNTRY as c WHERE c.CODE = @COUNTRYCODE)
  BEGIN
      RAISERROR ('Record not found: @COUNTRYCODE', 11,1);
	END

 IF NOT EXISTS(SELECT TOP 1 s.ID FROM [STATE] as s WHERE s.CODE = @STATECODE)
  BEGIN
      RAISERROR ('Record not found: @STATECODE', 11,1);
	END

 IF NOT EXISTS(SELECT TOP 1 s.ID FROM CITY as s WHERE s.NAME = @CITYNAME)
  BEGIN
      RAISERROR ('Record not found: @CITYNAME', 11,1);
	END

IF NOT EXISTS(SELECT TOP 1 t.ID FROM TEAM as t WHERE t.ID = @TeamId)
  BEGIN
      RAISERROR ('Record not found: @TeamId', 11,1);
	END

IF NOT EXISTS(SELECT TOP 1 t.ID FROM TEAM as t WHERE t.ID = @TeamId)
  BEGIN
      RAISERROR ('Record not found: @TeamId', 11,1);
	END

IF NOT EXISTS(SELECT TOP 1 age.ID FROM AGEGROUP as age WHERE age.ID = @AgeGroupId)
  BEGIN
      RAISERROR ('Record not found: @AgeGroupId', 11,1);
	END


 INSERT INTO [USER] (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PHONE, DAIL_CODE, PINCODE, ADDRESS, COUNTRYID, STATEID, CITYID)
 VALUES (@UserName, @FirstName, @LastName, @EMAIL, @PHONE, @DAIL_CODE, @PINCODE, @ADDRESS,  
 (SELECT TOP 1 c.ID FROM COUNTRY as c WHERE c.CODE = @COUNTRYCODE),  
 (SELECT TOP 1 s.ID FROM STATE as s WHERE s.CODE = @STATECODE), 
 (SELECT TOP 1 ci.ID FROM CITY as ci WHERE ci.NAME = @CITYNAME))

END TRY

BEGIN CATCH
 SELECT ERROR_MESSAGE() as error
END CATCH

 DECLARE @curUser int = (SELECT Top 1 U.ID FROM [USER] as U WHERE U.USERNAME = @UserName)

 if(@curUser > 0)
 BEGIN

 INSERT INTO USER_REGISTRATION (USERID, TEAMID, POSITIONID, AGEGROUPID)
 VALUES (@curUser, @TeamId, @PositionIds, @AgeGroupId);

 SELECT 200 as success

END
GO
USE [master]
GO
ALTER DATABASE [football_registration] SET  READ_WRITE 
GO
