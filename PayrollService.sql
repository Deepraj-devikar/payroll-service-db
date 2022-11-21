-- show all databases
SHOW DATABASES;

-- creating payroll_service database
CREATE DATABASE payroll_service;

-- will show all databases including payroll_service
SHOW DATABASES;

-- database change to use payroll_service database
USE payroll_service;

-- creating table employee payroll with id, employee name, salary, start date
CREATE TABLE employee_payroll (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(30),
    salary DECIMAL(20, 10),
    start_date DATE
);

-- inserting data to employee payroll table
INSERT INTO employee_payroll (employee_name, salary, start_date) 
VALUES ('Pushpak', 20000, '2018-02-19');

INSERT INTO employee_payroll 
VALUES (2, 'Jayesh', 50000.54, '2019-02-19');

INSERT INTO employee_payroll 
VALUES (3, 'Rupesh', 60500.54, '2019-05-19'), 
(4, 'Ram', 999999.999, '2020-06-25');

INSERT INTO employee_payroll (employee_name, salary, start_date) 
VALUES ('Narang', 555555.56, '2021-10-21'), 
('Sarang', 6666666.78, '2020-08-08');

-- read data from employee payroll table
SELECT * FROM employee_payroll;