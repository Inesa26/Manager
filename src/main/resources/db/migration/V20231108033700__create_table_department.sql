CREATE TABLE departments
(
    department_id   NUMBER(4) PRIMARY KEY,
    department_name VARCHAR2(30) NOT NULL,
    location   VARCHAR2(30)    NOT NULL
);

CREATE SEQUENCE departments_seq START WITH 10 INCREMENT BY 10 MAXVALUE 1000 NOCACHE;

ALTER TABLE departments MODIFY department_id DEFAULT departments_seq.nextval;


COMMENT ON TABLE departments IS 'Stores information about organizational departments.';
COMMENT ON COLUMN departments.department_id IS 'Primary key uniquely identifying each department.';
COMMENT ON COLUMN departments.department_name IS 'Name of the department, marked as NOT NULL to ensure a valid entry.';
COMMENT ON COLUMN departments.location IS 'Location or physical address of the department, marked as NOT NULL.'