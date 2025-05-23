-- MySQL World Database Sample
-- Source: https://dev.mysql.com/doc/index-other.html

-- Create tables
CREATE TABLE country (
  code CHAR(3) NOT NULL,
  name VARCHAR(52) NOT NULL,
  continent VARCHAR(50) NOT NULL,
  region VARCHAR(26) NOT NULL,
  surface_area FLOAT(10,2) NOT NULL,
  indep_year SMALLINT(6),
  population INT(11) NOT NULL,
  life_expectancy FLOAT(3,1),
  gnp FLOAT(10,2),
  gnp_old FLOAT(10,2),
  local_name VARCHAR(45) NOT NULL,
  government_form VARCHAR(45) NOT NULL,
  head_of_state VARCHAR(60),
  capital INT(11),
  code2 CHAR(2) NOT NULL,
  PRIMARY KEY (code)
);

CREATE TABLE city (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(35) NOT NULL,
  country_code CHAR(3) NOT NULL,
  district VARCHAR(20) NOT NULL,
  population INT(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (country_code) REFERENCES country (code)
);

CREATE TABLE countrylanguage (
  country_code CHAR(3) NOT NULL,
  language VARCHAR(30) NOT NULL,
  is_official ENUM('T','F') NOT NULL,
  percentage FLOAT(4,1) NOT NULL,
  PRIMARY KEY (country_code, language),
  FOREIGN KEY (country_code) REFERENCES country (code)
);

-- Insert sample data for country
INSERT INTO country VALUES ('USA','United States','North America','North America',9363520.00,1776,331002651,78.8,14264600.00,8510700.00,'United States','Federal Republic','Joe Biden',3813,'US');
INSERT INTO country VALUES ('CAN','Canada','North America','North America',9970610.00,1867,37742154,81.3,598300.00,486000.00,'Canada','Constitutional Monarchy','Elizabeth II',1822,'CA');
INSERT INTO country VALUES ('MEX','Mexico','North America','Central America',1958201.00,1810,126014024,75.4,474400.00,474400.00,'México','Federal Republic','Andrés Manuel López Obrador',2515,'MX');
INSERT INTO country VALUES ('GBR','United Kingdom','Europe','British Islands',242900.00,1066,67886011,80.2,1378330.00,1296830.00,'United Kingdom','Constitutional Monarchy','Elizabeth II',456,'GB');
INSERT INTO country VALUES ('FRA','France','Europe','Western Europe',551500.00,843,65273511,81.9,1243670.00,1176820.00,'France','Republic','Emmanuel Macron',2974,'FR');
INSERT INTO country VALUES ('DEU','Germany','Europe','Western Europe',357022.00,1955,83783942,81.0,1935600.00,1821500.00,'Deutschland','Federal Republic','Frank-Walter Steinmeier',3068,'DE');
INSERT INTO country VALUES ('JPN','Japan','Asia','Eastern Asia',377829.00,-660,126476461,84.5,4310000.00,4286000.00,'Nihon/Nippon','Constitutional Monarchy','Naruhito',1532,'JP');
INSERT INTO country VALUES ('CHN','China','Asia','Eastern Asia',9572900.00,-1523,1439323776,76.1,982268.00,917719.00,'Zhongquo','People\'s Republic','Xi Jinping',1891,'CN');
INSERT INTO country VALUES ('IND','India','Asia','Southern and Central Asia',3287263.00,1947,1380004385,69.7,447114.00,430572.00,'Bharat/India','Federal Republic','Ram Nath Kovind',1109,'IN');
INSERT INTO country VALUES ('BRA','Brazil','South America','South America',8547403.00,1822,212559417,75.9,452387.00,506649.00,'Brasil','Federal Republic','Jair Bolsonaro',211,'BR');

-- Insert sample data for city
INSERT INTO city (name, country_code, district, population) VALUES ('New York', 'USA', 'New York', 8175133);
INSERT INTO city (name, country_code, district, population) VALUES ('Los Angeles', 'USA', 'California', 3971883);
INSERT INTO city (name, country_code, district, population) VALUES ('Chicago', 'USA', 'Illinois', 2720546);
INSERT INTO city (name, country_code, district, population) VALUES ('Toronto', 'CAN', 'Ontario', 2731571);
INSERT INTO city (name, country_code, district, population) VALUES ('Montreal', 'CAN', 'Quebec', 1704694);
INSERT INTO city (name, country_code, district, population) VALUES ('Mexico City', 'MEX', 'Distrito Federal', 8851080);
INSERT INTO city (name, country_code, district, population) VALUES ('London', 'GBR', 'England', 8908081);
INSERT INTO city (name, country_code, district, population) VALUES ('Paris', 'FRA', 'Île-de-France', 2148271);
INSERT INTO city (name, country_code, district, population) VALUES ('Berlin', 'DEU', 'Berlin', 3669491);
INSERT INTO city (name, country_code, district, population) VALUES ('Tokyo', 'JPN', 'Tokyo', 13929286);
INSERT INTO city (name, country_code, district, population) VALUES ('Shanghai', 'CHN', 'Shanghai', 24281400);
INSERT INTO city (name, country_code, district, population) VALUES ('Mumbai', 'IND', 'Maharashtra', 12442373);
INSERT INTO city (name, country_code, district, population) VALUES ('São Paulo', 'BRA', 'São Paulo', 12325232);

-- Insert sample data for countrylanguage
INSERT INTO countrylanguage VALUES ('USA', 'English', 'T', 86.2);
INSERT INTO countrylanguage VALUES ('USA', 'Spanish', 'F', 10.7);
INSERT INTO countrylanguage VALUES ('CAN', 'English', 'T', 59.3);
INSERT INTO countrylanguage VALUES ('CAN', 'French', 'T', 23.2);
INSERT INTO countrylanguage VALUES ('MEX', 'Spanish', 'T', 92.7);
INSERT INTO countrylanguage VALUES ('GBR', 'English', 'T', 97.3);
INSERT INTO countrylanguage VALUES ('FRA', 'French', 'T', 93.6);
INSERT INTO countrylanguage VALUES ('DEU', 'German', 'T', 91.8);
INSERT INTO countrylanguage VALUES ('JPN', 'Japanese', 'T', 99.1);
INSERT INTO countrylanguage VALUES ('CHN', 'Chinese', 'T', 92.0);
INSERT INTO countrylanguage VALUES ('IND', 'Hindi', 'T', 41.0);
INSERT INTO countrylanguage VALUES ('IND', 'English', 'F', 10.0);
INSERT INTO countrylanguage VALUES ('BRA', 'Portuguese', 'T', 97.5);