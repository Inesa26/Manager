CREATE OR REPLACE VIEW emp_details_view AS
SELECT e.employee_id,
       e.department_id,
       d.department_name
FROM employees e,
     departments d

WHERE e.department_id = d.department_id
    WITH READ ONLY;

