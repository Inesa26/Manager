package com.webapp.employeemanager.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.employeemanager.model.Department;
import com.webapp.employeemanager.model.Employee;
import com.webapp.employeemanager.model.EmployeeDto;
import com.webapp.employeemanager.repository.DepartmentRepository;
import com.webapp.employeemanager.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentServiceImpl departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeDto convertToDTO(Employee employee) {
        return EmployeeDto.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .department(employee.getDepartment())
                .build();
    }

    public Employee convertToEntity(EmployeeDto employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(employeeDTO.getDepartment());


        return employee;
    }

    @Override
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(this::convertToDTO);
    }

    @Override
    public boolean create(EmployeeDto employeeDTO) {
        if (!employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            Long departmentId = employeeDTO.getDepartment().getId();

            if (departmentId != null) {
                Optional<Department> departmentOptional = departmentRepository.findById(departmentId);

                if (departmentOptional.isPresent()) {
                    // Use the existing department with the specified id
                    Employee employee = convertToEntity(employeeDTO);
                    employee.setDepartment(departmentOptional.get());
                    employeeRepository.save(employee);
                    return true;
                } else {
                    // Department with the specified id does not exist

                    return false;
                }
            } else {
                // Create a new department if id is not specified
                Department department = employeeDTO.getDepartment();
                departmentService.create(departmentService.convertToDTO(department));

                Optional<Department> departmentOptional = departmentRepository.findByDepartmentNameAndLocation(
                        department.getDepartmentName(), department.getLocation());

                if (departmentOptional.isPresent()) {
                    // Use the newly created or existing department
                    Employee employee = convertToEntity(employeeDTO);
                    employee.setDepartment(departmentOptional.get());
                    employeeRepository.save(employee);
                    return true;
                } else {
                    // department could not be created or found
                    return false;
                }
            }
        } else {
            // Employee with the specified email already exists
            return false;
        }
    }

    @Override
    public boolean update(Long id, EmployeeDto newEmployeeDto) {
        Optional<Employee> existingEmployeeDto = employeeRepository.findById(id);
        if (existingEmployeeDto.isPresent()) {
            Employee existingEmployee = existingEmployeeDto.get();

            existingEmployee.setFirstName(newEmployeeDto.getFirstName());
            existingEmployee.setLastName(newEmployeeDto.getLastName());
            existingEmployee.setEmail(newEmployeeDto.getEmail());
            existingEmployee.setPhoneNumber(newEmployeeDto.getPhoneNumber());
            existingEmployee.setSalary(newEmployeeDto.getSalary());

            Department department = newEmployeeDto.getDepartment();
            if (department != null) {
                if (department.getId() != null) {
                    Optional<Department> existingDepartmentOptional = departmentRepository.findById(department.getId());
                    existingDepartmentOptional.ifPresent(existingEmployee::setDepartment);
                } else if (department.getDepartmentName() != null && department.getLocation() != null) {
                    Optional<Department> existingDepartmentOptional = departmentRepository.findByDepartmentNameAndLocation(
                            department.getDepartmentName(), department.getLocation());

                    if (existingDepartmentOptional.isPresent()) {
                        // Use the existing department
                        existingDepartmentOptional.ifPresent(existingEmployee::setDepartment);
                    } else {
                        // Create a new department only if it hasn't been saved yet
                        Department newDepartment = new Department();
                        newDepartment.setDepartmentName(department.getDepartmentName());
                        newDepartment.setLocation(department.getLocation());

                        if (!departmentRepository.existsByDepartmentNameAndLocation(newDepartment.getDepartmentName(), newDepartment.getLocation())) {
                            Department savedDepartment = departmentRepository.save(newDepartment);
                            existingEmployee.setDepartment(savedDepartment);
                        } else {
                            // Set the existing department without saving it
                            existingEmployee.setDepartment(newDepartment);
                        }
                    }
                }
            }
            employeeRepository.save(existingEmployee);
            return true;
        } else {
            //there is no person with specified id
            return false;
        }
    }


    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

}
