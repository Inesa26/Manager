CREATE TABLE employees
(
    employee_id    NUMBER(6) PRIMARY KEY,
    first_name     VARCHAR2(20),
    last_name      VARCHAR2(25)        NOT NULL,
    email          VARCHAR2(50) UNIQUE NOT NULL,
    phone_number   VARCHAR2(20),
    salary         NUMBER(8, 2),
    department_id  NUMBER(4)           NOT NULL,
    CONSTRAINT emp_salary_min CHECK (salary > 0)
);

CREATE SEQUENCE employees_seq NOCACHE;

ALTER TABLE employees
    MODIFY employee_id DEFAULT employees_seq.nextval;

COMMENT ON TABLE employees IS 'Stores information about employees within the organization.';
COMMENT ON COLUMN employees.employee_id IS 'Primary key identifying each employee.';
COMMENT ON COLUMN employees.first_name IS 'First name of the employee.';
COMMENT ON COLUMN employees.last_name IS 'Last name of the employee, marked as NOT NULL to ensure a valid entry.';
COMMENT ON COLUMN employees.email IS 'Unique email address for the employee, marked as NOT NULL.';
COMMENT ON COLUMN employees.phone_number IS 'Contact phone number for the employee, if available.';
COMMENT ON COLUMN employees.salary IS 'Employees monthly salary.';
COMMENT ON COLUMN employees.department_id IS 'Foreign key referencing the department to which the employee belongs, marked as NOT NULL to ensure proper department association.';
