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

-- check sum of all male employees salary
SELECT SUM(salary) FROM employee_payroll WHERE gender = 'M' GROUP BY gender;
/* OUTPUT
+---------------------+
| SUM(salary)         |
+---------------------+
| 34139035.6690000000 |
+---------------------+
*/

-- check sum of all female employees salary
SELECT SUM(salary) FROM employee_payroll WHERE gender = 'F' GROUP BY gender;
/* OUTPUT
+--------------------+
| SUM(salary)        |
+--------------------+
| 6349767.3300000000 |
+--------------------+
*/

-- check average of all male employees salary
SELECT AVG(salary) FROM employee_payroll WHERE gender = 'M' GROUP BY gender;
/* OUTPUT
+------------------------+
| AVG(salary)            |
+------------------------+
| 4877005.09557142857143 |
+------------------------+
*/

-- check average of all female employees salary
SELECT AVG(salary) FROM employee_payroll WHERE gender = 'F' GROUP BY gender;
/* OUTPUT
+------------------------+
| AVG(salary)            |
+------------------------+
| 3174883.66500000000000 |
+------------------------+
*/

-- check minimum salary in male employees salary
SELECT MIN(salary) FROM employee_payroll WHERE gender = 'M' GROUP BY gender;
/* OUTPUT
+------------------+
| MIN(salary)      |
+------------------+
| 20000.0000000000 |
+------------------+
*/

-- check minimum salary in female employees salary
SELECT MIN(salary) FROM employee_payroll WHERE gender = 'F' GROUP BY gender;
/* OUTPUT
+-------------------+
| MIN(salary)       |
+-------------------+
| 482514.2100000000 |
+-------------------+
*/

-- check maximum salary in male employees salary
SELECT MAX(salary) FROM employee_payroll WHERE gender = 'M' GROUP BY gender;
/* OUTPUT
+---------------------+
| MAX(salary)         |
+---------------------+
| 25786312.2500000000 |
+---------------------+
*/

-- check maximum salary in female employees salary
SELECT MAX(salary) FROM employee_payroll WHERE gender = 'F' GROUP BY gender;
/* OUTPUT
+--------------------+
| MAX(salary)        |
+--------------------+
| 5867253.1200000000 |
+--------------------+
*/

-- check count of male employees who are getting salary
SELECT COUNT(salary) FROM employee_payroll WHERE gender = 'M' GROUP BY gender;
/* OUTPUT
+---------------+
| COUNT(salary) |
+---------------+
|             7 |
+---------------+
*/

-- check count of female employees who are getting salary
SELECT COUNT(salary) FROM employee_payroll WHERE gender = 'F' GROUP BY gender;
/* OUTPUT
+---------------+
| COUNT(salary) |
+---------------+
|             2 |
+---------------+
*/

-----------------AFTER CREATION OF EMPLOYEE TABLE----------------------------
-- have to store employee information insted employee name only
-- add employee id
ALTER TABLE employee_payroll 
ADD employee_id INT, 
ADD FOREIGN KEY (employee_id) REFERENCES employee(id);

-- update employee id in employee payroll accordin to employee name
UPDATE employee_payroll
INNER JOIN employee ON employee_payroll.employee_name = employee.employee_name
SET employee_payroll.employee_id = employee.id;

-- move employee id column next to employee name column
ALTER TABLE employee_payroll
MODIFY employee_id INT AFTER employee_name;

-- drop employee name column
ALTER TABLE employee_payroll DROP employee_name;

SELECT * FROM employee_payroll;
/* OUTPUT
+----+-------------+--------+---------------------+------------+
| id | employee_id | gender | salary              | start_date |
+----+-------------+--------+---------------------+------------+
|  1 |           1 | M      |    20000.0000000000 | 2018-02-19 |
|  2 |           2 | M      |    50000.5400000000 | 2019-02-19 |
|  3 |           4 | M      |    60500.5400000000 | 2019-05-19 |
|  4 |           3 | M      |   999999.9990000000 | 2020-06-25 |
|  5 |           5 | M      |   555555.5600000000 | 2021-10-21 |
|  6 |           6 | M      |  6666666.7800000000 | 2020-08-08 |
|  7 |           7 | M      | 25786312.2500000000 | 2022-01-01 |
|  8 |           8 | F      |  5867253.1200000000 | 2020-04-15 |
|  9 |           9 | F      |   482514.2100000000 | 2021-01-05 |
+----+-------------+--------+---------------------+------------+
*/

-----------------------------------AFTER ADDING EMPLOYEE GENDER AND START DATE TO EMPLOYEE TABLE-----------------------
ALTER TABLE employee_payroll DROP gender, DROP start_date;

-- retrive employee payroll data
SELECT e_p.id AS employee_payroll_id, e.employee_name, e.gender, e_p.salary, e.start_date
FROM employee_payroll AS e_p LEFT JOIN employee AS e ON e_p.employee_id = e.id;
/* OUTPUT
+---------------------+---------------+--------+---------------------+------------+
| employee_payroll_id | employee_name | gender | salary              | start_date |
+---------------------+---------------+--------+---------------------+------------+
|                   1 | Pushpak       | M      |    20000.0000000000 | 2018-02-19 |
|                   2 | Jayesh        | M      |    50000.5400000000 | 2019-02-19 |
|                   3 | Rupesh        | M      |    60500.5400000000 | 2019-05-19 |
|                   4 | Ram           | M      |   999999.9990000000 | 2020-06-25 |
|                   5 | Narang        | M      |   555555.5600000000 | 2021-10-21 |
|                   6 | Sarang        | M      |  6666666.7800000000 | 2020-08-08 |
|                   7 | Bill          | M      | 25786312.2500000000 | 2022-01-01 |
|                   8 | Sita          | F      |  5867253.1200000000 | 2020-04-15 |
|                   9 | Gita          | F      |   482514.2100000000 | 2021-01-05 |
+---------------------+---------------+--------+---------------------+------------+
*/