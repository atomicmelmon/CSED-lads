CREATE TABLE account(int id UNSIGNED AUTO_INCREMENT,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
password VARCHAR(72) NOT NULL,
dob DATE,
created DATETIME,
PRIMARY KEY(id));