package com.webapp.employeemanager.service;

import java.util.List;
import java.util.Optional;

import com.webapp.employeemanager.model.DepartmentDto;

public interface DepartmentService {
    List<DepartmentDto> findAll();

    Optional<DepartmentDto> findById(Long id);

    boolean create(DepartmentDto department);

    boolean update(Long id, DepartmentDto newDepartmentDto);

    void delete(Long id);
}
