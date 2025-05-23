-- Country table
CREATE TABLE country (
  code CHAR(3) NOT NULL,
  name VARCHAR(52) NOT NULL,
  continent VARCHAR(50) NOT NULL,
  region VARCHAR(26) NOT NULL,
  surface_area FLOAT NOT NULL,
  indep_year INT,
  population INT NOT NULL,
  life_expectancy FLOAT,
  gnp FLOAT,
  gnp_old FLOAT,
  local_name VARCHAR(45) NOT NULL,
  government_form VARCHAR(45) NOT NULL,
  head_of_state VARCHAR(60),
  capital INT,
  code2 CHAR(2) NOT NULL,
  PRIMARY KEY (code)
);

-- City table
CREATE TABLE city (
  id INT AUTO_INCREMENT,
  name VARCHAR(35) NOT NULL,
  country_code CHAR(3) NOT NULL,
  district VARCHAR(20) NOT NULL,
  population INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (country_code) REFERENCES country(code)
);

-- CountryLanguage table
CREATE TABLE countrylanguage (
  country_code CHAR(3) NOT NULL,
  language VARCHAR(30) NOT NULL,
  is_official CHAR(1) NOT NULL,
  percentage FLOAT NOT NULL,
  PRIMARY KEY (country_code, language),
  FOREIGN KEY (country_code) REFERENCES country(code)
);