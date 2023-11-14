package com.webapp.employeemanager.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.employeemanager.model.EmployeeDto;
import com.webapp.employeemanager.service.EmployeeService;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping // GET localhost:8081/employees
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{id}") // GET localhost:8081/employees/1
    public ResponseEntity<Optional<EmployeeDto>> getById(@PathVariable Long id) {
        Optional<EmployeeDto> employeeDTO = employeeService.findById(id);
        if (employeeDTO.isPresent()) {
            return ResponseEntity.ok(employeeDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping //POST localhost:8081/employees
    public ResponseEntity create(@RequestBody EmployeeDto employee) {
        if (employeeService.create(employee)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")  //PUT localhost:8081/employees/
    public ResponseEntity update(@PathVariable Long id, @RequestBody EmployeeDto newEmployeeDto) {
        if (employeeService.update(id, newEmployeeDto)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}") //DELETE localhost:8081/employees/
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<EmployeeDto> employeeDTO = employeeService.findById(id);
        if (employeeDTO.isPresent()) {
            employeeService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}