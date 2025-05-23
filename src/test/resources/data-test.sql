-- Insert test countries
INSERT INTO country (code, name, continent, region, surface_area, indep_year, population, life_expectancy, gnp, gnp_old, local_name, government_form, head_of_state, capital, code2)
VALUES 
('USA', 'United States', 'North America', 'North America', 9363520, 1776, 278357000, 77.1, 8510700, 8110900, 'United States', 'Federal Republic', 'George W. Bush', 3813, 'US'),
('KOR', 'South Korea', 'Asia', 'Eastern Asia', 99434, 1948, 46844000, 74.4, 320749, 442544, 'Taehan Minguk', 'Republic', 'Kim Dae-jung', 2331, 'KR'),
('JPN', 'Japan', 'Asia', 'Eastern Asia', 377829, -660, 126714000, 80.7, 3787042, 4280222, 'Nihon/Nippon', 'Constitutional Monarchy', 'Akihito', 1532, 'JP'),
('GBR', 'United Kingdom', 'Europe', 'British Islands', 242900, 1066, 59623400, 77.7, 1378330, 1296830, 'United Kingdom', 'Constitutional Monarchy', 'Elisabeth II', 456, 'GB'),
('FRA', 'France', 'Europe', 'Western Europe', 551500, 843, 59225700, 78.8, 1424285, 1392448, 'France', 'Republic', 'Jacques Chirac', 2974, 'FR');

-- Insert test cities
INSERT INTO city (name, country_code, district, population)
VALUES 
('New York', 'USA', 'New York', 8008278),
('Los Angeles', 'USA', 'California', 3694820),
('Chicago', 'USA', 'Illinois', 2896016),
('Seoul', 'KOR', 'Seoul', 9981619),
('Busan', 'KOR', 'Pusan', 3804522),
('Tokyo', 'JPN', 'Tokyo-to', 7980230),
('Osaka', 'JPN', 'Osaka', 2595674),
('London', 'GBR', 'England', 7285000),
('Birmingham', 'GBR', 'England', 1013000),
('Paris', 'FRA', 'Île-de-France', 2125246),
('Marseille', 'FRA', 'Provence-Alpes-Côte', 798430);

-- Insert test country languages
INSERT INTO countrylanguage (country_code, language, is_official, percentage)
VALUES 
('USA', 'English', 'T', 86.2),
('USA', 'Spanish', 'F', 7.5),
('KOR', 'Korean', 'T', 99.9),
('JPN', 'Japanese', 'T', 99.1),
('GBR', 'English', 'T', 97.3),
('GBR', 'Welsh', 'F', 0.7),
('FRA', 'French', 'T', 93.6),
('FRA', 'Arabic', 'F', 1.9);