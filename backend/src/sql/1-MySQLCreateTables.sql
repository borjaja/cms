-- Indexes for primary keys have been explicitly created.
-- SET FOREIGN_KEY_CHECKS = 0;
ALTER TABLE Product DROP FOREIGN KEY ProductWinnerIdFK;
DROP TABLE Bid;
DROP TABLE Product;
DROP TABLE User;
DROP TABLE Category;
-- SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE Category (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    CONSTRAINT CategoryPK PRIMARY KEY (id),
    CONSTRAINT CategoryNameUniqueKey UNIQUE (name)
) ENGINE = InnoDB;

CREATE INDEX CategoryIndexByName ON Category (name);

CREATE TABLE User (
    id BIGINT NOT NULL AUTO_INCREMENT,
    userName VARCHAR(60) COLLATE latin1_bin NOT NULL,
    password VARCHAR(60) NOT NULL,
    firstName VARCHAR(60) NOT NULL,
    lastName VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
    role TINYINT NOT NULL,
    CONSTRAINT UserPK PRIMARY KEY (id),
    CONSTRAINT UserNameUniqueKey UNIQUE (userName)
) ENGINE = InnoDB;

CREATE INDEX UserIndexByUserName ON User (userName);

CREATE TABLE Product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    deliverInfo VARCHAR(2000) NOT NULL,
    date DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    price DECIMAL(11, 2) NOT NULL,
    actualPrice DECIMAL(11, 2) NOT NULL,
    categoryId BIGINT NOT NULL,
    productVersion BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    winnerId BIGINT,
    CONSTRAINT ProductPK PRIMARY KEY (id),
    CONSTRAINT ProductCategoryIdFK FOREIGN KEY(categoryId)
        REFERENCES Category (id),
    CONSTRAINT ProductUserIdFK FOREIGN KEY(userId)
        REFERENCES User (id)
) ENGINE = InnoDB;

CREATE INDEX ProductIndexByName ON Category (name);

CREATE TABLE Bid(
    id BIGINT NOT NULL AUTO_INCREMENT,
    productId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    date DATETIME NOT NULL,
    price DECIMAL(11, 2) NOT NULL,
    CONSTRAINT BidPK PRIMARY KEY (id),
    CONSTRAINT BidProductIdFK FOREIGN KEY(productId)
        REFERENCES Product (id),
    CONSTRAINT BidUserIdFK FOREIGN KEY(userId)
        REFERENCES User (id)
) ENGINE = InnoDB;


ALTER TABLE Product ADD CONSTRAINT ProductWinnerIdFK FOREIGN KEY (winnerId) REFERENCES Bid (id);

