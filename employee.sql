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
