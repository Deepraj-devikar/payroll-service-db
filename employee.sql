-- create department table
CREATE TABLE department (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(20) NOT NULL
);

-- insert 2 departments
INSERT INTO department (department_name) 
VALUES ('computer science'), ('electrical');

-- retrive department table data
SELECT * FROM department;

-- create employee table with phone, address, department attribute
CREATE TABLE employee (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    employee_name VARCHAR(30),
    address_id INT,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES department(id)
);
-- create phone table for storing employees phone number
CREATE TABLE phone (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(15) NOT NULL,
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

-- create address table for storing employees address
CREATE TABLE employee_address (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    line_1 VARCHAR(30) NOT NULL,
    line_2 VARCHAR(30),
    city VARCHAR(15),
    state_name VARCHAR(15),
    country VARCHAR(15),
    zip VARCHAR(15),
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

-- add foreign key constraint for employee table address id
ALTER TABLE employee 
ADD FOREIGN KEY (address_id) REFERENCES employee_address(id);

-- employee department is non nullable field
ALTER TABLE employee
MODIFY department_id INT NOT NULL;

-- set employee address default value
ALTER TABLE employee_address
MODIFY line_1 VARCHAR(30) NOT NULL DEFAULT 'line 1',
MODIFY line_2 VARCHAR(30) DEFAULT 'line 2',
MODIFY city VARCHAR(15) NOT NULL DEFAULT 'city',
MODIFY state_name VARCHAR(15) NOT NULL DEFAULT 'state',
MODIFY country VARCHAR(15) NOT NULL DEFAULT 'country',
MODIFY zip VARCHAR(15) NOT NULL DEFAULT '000000',
MODIFY employee_id INT NOT NULL;

-- insert employee data to employee table with department id (department id is not null)
INSERT INTO employee (employee_name, department_id)
VALUES ('Pushpak', 1), ('Jayesh', 2), ('Ram', 2), ('Rupesh', 1),
('Narang', 2), ('Sarang', 1), ('Bill', 1), ('Sita', 2), ('Gita', 1);

SELECT * FROM employee;
/* OUTPUT
+----+---------------+------------+---------------+
| id | employee_name | address_id | department_id |
+----+---------------+------------+---------------+
|  1 | Pushpak       |       NULL |             1 |
|  2 | Jayesh        |       NULL |             2 |
|  3 | Ram           |       NULL |             2 |
|  4 | Rupesh        |       NULL |             1 |
|  5 | Narang        |       NULL |             2 |
|  6 | Sarang        |       NULL |             1 |
|  7 | Bill          |       NULL |             1 |
|  8 | Sita          |       NULL |             2 |
|  9 | Gita          |       NULL |             1 |
+----+---------------+------------+---------------+
*/

-- insert employees phone numbers
INSERT INTO phone (phone_number, employee_id)
VALUES ('9852541256', 1), ('4782561472', 2), ('7861542354', 1), 
('7854123568', 3), ('789254175', 2), ('781475473214', 4), 
('8752689874', 5), ('785786589', 6), ('4785682541', 7), 
('875471243', 8), ('8965748741', 9);

-- insert employees address with address values
INSERT INTO employee_address (line_1, line_2, city, state_name, country, zip, employee_id)
VALUES ('godibar sq', 'shop name', 'NGP', 'MH', 'IND', '440015', 1),
('maskasath', 'near mandir', 'NGP', 'MH', 'IND', '441125', 2),
('agresan sq', 'garage name', 'NGP', 'MH', 'IND', '442571', 3),
('jhasi rani sq', 'complex number', 'NGP', 'MH', 'IND', '445782', 4),
('mahal', 'shop name', 'NGP', 'MH', 'IND', '445471', 8);

-- insert employees address without address values it will take default address values
INSERT INTO employee_address (employee_id)
VALUES (5), (6), (7), (9);

-- set address ids for employee table
UPDATE employee SET address_id = 1 WHERE id = 5;
UPDATE employee SET address_id = 2 WHERE id = 6;
UPDATE employee SET address_id = 3 WHERE id = 7;
UPDATE employee SET address_id = 4 WHERE id = 9;
UPDATE employee SET address_id = 5 WHERE id = 1;
UPDATE employee SET address_id = 6 WHERE id = 2;
UPDATE employee SET address_id = 7 WHERE id = 3;
UPDATE employee SET address_id = 8 WHERE id = 4;
UPDATE employee SET address_id = 9 WHERE id = 8;

SELECT * FROM employee;
/* OUTPUT
+----+---------------+------------+---------------+
| id | employee_name | address_id | department_id |
+----+---------------+------------+---------------+
|  1 | Pushpak       |          5 |             1 |
|  2 | Jayesh        |          6 |             2 |
|  3 | Ram           |          7 |             2 |
|  4 | Rupesh        |          8 |             1 |
|  5 | Narang        |          1 |             2 |
|  6 | Sarang        |          2 |             1 |
|  7 | Bill          |          3 |             1 |
|  8 | Sita          |          9 |             2 |
|  9 | Gita          |          4 |             1 |
+----+---------------+------------+---------------+
*/

SELECT * FROM phone;
/* OUTPUT
+----+--------------+-------------+
| id | phone_number | employee_id |
+----+--------------+-------------+
|  1 | 9852541256   |           1 |
|  2 | 4782561472   |           2 |
|  3 | 7861542354   |           1 |
|  4 | 7854123568   |           3 |
|  5 | 789254175    |           2 |
|  6 | 781475473214 |           4 |
|  7 | 8752689874   |           5 |
|  8 | 785786589    |           6 |
|  9 | 4785682541   |           7 |
| 10 | 875471243    |           8 |
| 11 | 8965748741   |           9 |
+----+--------------+-------------+
*/

SELECT * FROM employee_address;
/* OUTPUT
+----+---------------+----------------+------+------------+---------+--------+-------------+
| id | line_1        | line_2         | city | state_name | country | zip    | employee_id |
+----+---------------+----------------+------+------------+---------+--------+-------------+
|  1 | line 1        | line 2         | city | state      | country | 000000 |           5 |
|  2 | line 1        | line 2         | city | state      | country | 000000 |           6 |
|  3 | line 1        | line 2         | city | state      | country | 000000 |           7 |
|  4 | line 1        | line 2         | city | state      | country | 000000 |           9 |
|  5 | godibar sq    | shop name      | NGP  | MH         | IND     | 440015 |           1 |
|  6 | maskasath     | near mandir    | NGP  | MH         | IND     | 441125 |           2 |
|  7 | agresan sq    | garage name    | NGP  | MH         | IND     | 442571 |           3 |
|  8 | jhasi rani sq | complex number | NGP  | MH         | IND     | 445782 |           4 |
|  9 | mahal         | shop name      | NGP  | MH         | IND     | 445471 |           8 |
+----+---------------+----------------+------+------------+---------+--------+-------------+
*/

-- retrive employee information by join
SELECT e.id As employee_id, e.employee_name, p.phone_number, 
e_a.line_1 AS address_line_1, e_a.line_2 AS address_line_2, 
e_a.city, e_a.state_name, e_a.country, e_a.zip, d.department_name
FROM employee AS e 
LEFT JOIN phone AS p ON e.id = p.employee_id
LEFT JOIN employee_address AS e_a ON e.id = e_a.employee_id
LEFT JOIN  department AS d ON d.id = e.department_id;
/* OUTPUT
+-------------+---------------+--------------+----------------+----------------+------+------------+---------+--------+------------------+
| employee_id | employee_name | phone_number | address_line_1 | address_line_2 | city | state_name | country | zip    | department_name  |
+-------------+---------------+--------------+----------------+----------------+------+------------+---------+--------+------------------+
|           1 | Pushpak       | 9852541256   | godibar sq     | shop name      | NGP  | MH         | IND     | 440015 | computer science |
|           1 | Pushpak       | 7861542354   | godibar sq     | shop name      | NGP  | MH         | IND     | 440015 | computer science |
|           2 | Jayesh        | 4782561472   | maskasath      | near mandir    | NGP  | MH         | IND     | 441125 | electrical       |
|           2 | Jayesh        | 789254175    | maskasath      | near mandir    | NGP  | MH         | IND     | 441125 | electrical       |
|           3 | Ram           | 7854123568   | agresan sq     | garage name    | NGP  | MH         | IND     | 442571 | electrical       |
|           4 | Rupesh        | 781475473214 | jhasi rani sq  | complex number | NGP  | MH         | IND     | 445782 | computer science |
|           5 | Narang        | 8752689874   | line 1         | line 2         | city | state      | country | 000000 | electrical       |
|           6 | Sarang        | 785786589    | line 1         | line 2         | city | state      | country | 000000 | computer science |
|           7 | Bill          | 4785682541   | line 1         | line 2         | city | state      | country | 000000 | computer science |
|           8 | Sita          | 875471243    | mahal          | shop name      | NGP  | MH         | IND     | 445471 | electrical       |
|           9 | Gita          | 8965748741   | line 1         | line 2         | city | state      | country | 000000 | computer science |
+-------------+---------------+--------------+----------------+----------------+------+------------+---------+--------+------------------+
*/