package com.webapp.employeemanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.employeemanager.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartmentNameAndLocation(String departmentName, String location);

    Optional<Department> findByDepartmentNameAndLocation(String departmentName, String location);


}