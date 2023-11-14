package com.webapp.employeemanager.service;

import java.util.List;
import java.util.Optional;

import com.webapp.employeemanager.model.EmployeeDto;

public interface EmployeeService {
    List<EmployeeDto> findAll();

    Optional<EmployeeDto> findById(Long id);

    boolean create(EmployeeDto employeeDTO);

    boolean update(Long id, EmployeeDto newEmployeeDto);

    void delete(Long id);
}
