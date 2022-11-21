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
