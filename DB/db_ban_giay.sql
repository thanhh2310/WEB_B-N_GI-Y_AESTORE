create database ae_store;
USE ae_store;

-- Create Role table
CREATE TABLE Role (
    RoleID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL UNIQUE
);

-- Create User table
CREATE TABLE User (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Phone VARCHAR(20),
    Status BOOLEAN DEFAULT TRUE,
    DoB DATE,
    Gender CHAR(1),
    FullName VARCHAR(100)
);

-- Create UserRole (QuantityRole in diagram) junction table
CREATE TABLE UserRole (
    UserID INT,
    RoleID INT,
    PRIMARY KEY (UserID, RoleID),
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (RoleID) REFERENCES Role(RoleID)
);

-- Create Address table
CREATE TABLE Address (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Address VARCHAR(255) NOT NULL,
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create Category table
CREATE TABLE Category (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Description TEXT
);

-- Create Brand table
CREATE TABLE Brand (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Description TEXT
);

-- Create Color table
CREATE TABLE Color (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL
);

-- Create Size table
CREATE TABLE Size (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL
);

-- Create Image table
CREATE TABLE Image (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Url VARCHAR(255) NOT NULL
);

-- Create Product table
CREATE TABLE Product (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Description TEXT,
    CategoryID INT,
    BrandID INT,
    FOREIGN KEY (CategoryID) REFERENCES Category(ID),
    FOREIGN KEY (BrandID) REFERENCES Brand(ID)
);

-- Create ProductDetail table
CREATE TABLE ProductDetail (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    ColorID INT,
    SizeID INT,
    ImageID INT,
    ProductID INT,
    Price DECIMAL(10,2) NOT NULL,
    Description TEXT,
    Quantity INT NOT NULL,
    FOREIGN KEY (ColorID) REFERENCES Color(ID),
    FOREIGN KEY (SizeID) REFERENCES Size(ID),
    FOREIGN KEY (ImageID) REFERENCES Image(ID),
    FOREIGN KEY (ProductID) REFERENCES Product(ID)
);

-- Create Cart table
CREATE TABLE Cart (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    DateCreate DATETIME DEFAULT CURRENT_TIMESTAMP,
    LastUpdate DATETIME ON UPDATE CURRENT_TIMESTAMP,
    CartItems INT,
    Total DECIMAL(10,2),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create CartDetail table
CREATE TABLE CartDetail (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    CartID INT,
    ProductID INT,
    Quantity INT NOT NULL,
    FOREIGN KEY (CartID) REFERENCES Cart(ID),
    FOREIGN KEY (ProductID) REFERENCES Product(ID)
);

-- Create Review table
CREATE TABLE Review (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    ProductID INT,
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    Comment TEXT,
    DateCreate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (ProductID) REFERENCES Product(ID)
);

-- Create Order table
CREATE TABLE `Order` (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    DateCreate DATETIME DEFAULT CURRENT_TIMESTAMP,
    Status VARCHAR(50),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create Coupon table
CREATE TABLE Coupon (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Value DECIMAL(10,2) NOT NULL,
    DateCreate DATETIME DEFAULT CURRENT_TIMESTAMP,
    DateExpire DATETIME NOT NULL,
    Quantity INT,
    Name VARCHAR(100),
    Code VARCHAR(50) UNIQUE
);

-- Create OrderDetail table
CREATE TABLE OrderDetail (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT,
    ProductID INT,
    CouponID INT,
    Total DECIMAL(10,2),
    Quantity INT NOT NULL,
    Attention VARCHAR(255),
    FOREIGN KEY (OrderID) REFERENCES `Order`(ID),
    FOREIGN KEY (ProductID) REFERENCES Product(ID),
    FOREIGN KEY (CouponID) REFERENCES Coupon(ID)
);

-- Create QuantityCoupon junction table
CREATE TABLE QuantityCoupon (
    CouponID INT,
    OrderDetailID INT,
    PRIMARY KEY (CouponID, OrderDetailID),
    FOREIGN KEY (CouponID) REFERENCES Coupon(ID),
    FOREIGN KEY (OrderDetailID) REFERENCES OrderDetail(ID)
);
