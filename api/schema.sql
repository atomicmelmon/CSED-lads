CREATE DATABASE csed;
USE csed;

CREATE TABLE account(id INT UNSIGNED AUTO_INCREMENT,
email VARCHAR(50) NOT NULL,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
password VARCHAR(72) NOT NULL,
dob DATE,
created DATETIME,
PRIMARY KEY(id));

CREATE TABLE account_session(id VARCHAR(36) NOT NULL,
account_id INT UNSIGNED NOT NULL,
started_at DATETIME,
PRIMARY KEY(id),
FOREIGN KEY(account_id) REFERENCES account(id));

CREATE TABLE mood(id INT UNSIGNED,
description VARCHAR(20) NOT NULL,
recorded_at DATE NOT NULL,
FOREIGN KEY(id) REFERENCES account(id));

CREATE TABLE tracker(id INT UNSIGNED,
started DATE NOT NULL,
lasted INT UNSIGNED NOT NULL,
cycle_length INT UNSIGNED NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES account(id));

CREATE TABLE intensity(id INT UNSIGNED,
intensity INT NOT NULL,
recorded_at DATE NOT NULL,
FOREIGN KEY(id) REFERENCES account(id));