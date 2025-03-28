DROP USER IF EXISTS 'spq'@'%';
CREATE USER IF NOT EXISTS 'spq'@'%' IDENTIFIED BY 'spq';

DROP SCHEMA IF EXISTS restapidb;
CREATE SCHEMA restapidb;

GRANT ALL ON restapidb.* TO 'spq'@'%';
FLUSH PRIVILEGES;