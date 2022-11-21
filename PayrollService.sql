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
/* OUTPUT
+----+---------------+--------------------+------------+
| id | employee_name | salary             | start_date |
+----+---------------+--------------------+------------+
|  1 | Pushpak       |   20000.0000000000 | 2018-02-19 |
|  2 | Jayesh        |   50000.5400000000 | 2019-02-19 |
|  3 | Rupesh        |   60500.5400000000 | 2019-05-19 |
|  4 | Ram           |  999999.9990000000 | 2020-06-25 |
|  5 | Narang        |  555555.5600000000 | 2021-10-21 |
|  6 | Sarang        | 6666666.7800000000 | 2020-08-08 |
+----+---------------+--------------------+------------+
*/

-- read data by employee name
SELECT salary FROM employee_payroll WHERE employee_name = 'Rupesh';
/* OUTPUT
+------------------+
| salary           |
+------------------+
| 60500.5400000000 |
+------------------+
*/

-- try to read 'Bill' data
SELECT salary FROM employee_payroll WHERE employee_name = 'Bill';
/* OUTPUT
Empty set (0.00 sec)
*/

-- insert 'Bill' data to table
INSERT INTO employee_payroll (employee_name, salary, start_date) 
VALUES ('Bill', 25786312.25, '2022-01-01');

SELECT salary FROM employee_payroll WHERE employee_name = 'Bill';
/* OUTPUT
+---------------------+
| salary              |
+---------------------+
| 25786312.2500000000 |
+---------------------+
*/

-- read data by start date in between date 2018-05-16 and todays date
SELECT * FROM employee_payroll WHERE start_date BETWEEN DATE('2018-05-16') AND DATE(NOW());
/* OUTPUT
+----+---------------+---------------------+------------+
| id | employee_name | salary              | start_date |
+----+---------------+---------------------+------------+
|  2 | Jayesh        |    50000.5400000000 | 2019-02-19 |
|  3 | Rupesh        |    60500.5400000000 | 2019-05-19 |
|  4 | Ram           |   999999.9990000000 | 2020-06-25 |
|  5 | Narang        |   555555.5600000000 | 2021-10-21 |
|  6 | Sarang        |  6666666.7800000000 | 2020-08-08 |
|  7 | Bill          | 25786312.2500000000 | 2022-01-01 |
+----+---------------+---------------------+------------+
*/

-- iserting sita and gita data to employee payroll table
INSERT INTO employee_payroll (employee_name, salary, start_date) 
VALUES ('Sita', 5867253.12, '2020-04-15'), 
('Gita', 482514.21, '2021-01-05');

-- add column gender after column employee name
ALTER TABLE employee_payroll ADD gender CHAR(1) AFTER employee_name;

-- read current data from employee payroll table
SELECT * FROM employee_payroll;
/* OUTPUT
+----+---------------+--------+---------------------+------------+
| id | employee_name | gender | salary              | start_date |
+----+---------------+--------+---------------------+------------+
|  1 | Pushpak       | NULL   |    20000.0000000000 | 2018-02-19 |
|  2 | Jayesh        | NULL   |    50000.5400000000 | 2019-02-19 |
|  3 | Rupesh        | NULL   |    60500.5400000000 | 2019-05-19 |
|  4 | Ram           | NULL   |   999999.9990000000 | 2020-06-25 |
|  5 | Narang        | NULL   |   555555.5600000000 | 2021-10-21 |
|  6 | Sarang        | NULL   |  6666666.7800000000 | 2020-08-08 |
|  7 | Bill          | NULL   | 25786312.2500000000 | 2022-01-01 |
|  8 | Sita          | NULL   |  5867253.1200000000 | 2020-04-15 |
|  9 | Gita          | NULL   |   482514.2100000000 | 2021-01-05 |
+----+---------------+--------+---------------------+------------+
*/

-- gender column has NULL value in every rows
-- have to update gender values
UPDATE employee_payroll SET gender = 'M' 
WHERE employee_name = 'Pushpak' 
OR employee_name = 'Jayesh' 
OR employee_name = 'Rupesh' 
OR employee_name = 'Ram' 
OR employee_name = 'Narang' 
OR employee_name = 'Sarang' 
OR employee_name = 'Bill';

UPDATE employee_payroll SET gender = 'F' 
WHERE employee_name = 'Sita' 
OR employee_name = 'Gita';

-- read current data from employee payroll table
SELECT * FROM employee_payroll;
/* OUTPUT
+----+---------------+--------+---------------------+------------+
| id | employee_name | gender | salary              | start_date |
+----+---------------+--------+---------------------+------------+
|  1 | Pushpak       | M      |    20000.0000000000 | 2018-02-19 |
|  2 | Jayesh        | M      |    50000.5400000000 | 2019-02-19 |
|  3 | Rupesh        | M      |    60500.5400000000 | 2019-05-19 |
|  4 | Ram           | M      |   999999.9990000000 | 2020-06-25 |
|  5 | Narang        | M      |   555555.5600000000 | 2021-10-21 |
|  6 | Sarang        | M      |  6666666.7800000000 | 2020-08-08 |
|  7 | Bill          | M      | 25786312.2500000000 | 2022-01-01 |
|  8 | Sita          | F      |  5867253.1200000000 | 2020-04-15 |
|  9 | Gita          | F      |   482514.2100000000 | 2021-01-05 |
+----+---------------+--------+---------------------+------------+
*/