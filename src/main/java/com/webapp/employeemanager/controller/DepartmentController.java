package com.webapp.employeemanager.controller;

import java.util.List;
import java.util.Optional;

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

import com.webapp.employeemanager.model.DepartmentDto;
import com.webapp.employeemanager.service.DepartmentService;


@RestController
@RequestMapping("/departments")
public class DepartmentController {

    DepartmentService departmentService;

    @GetMapping // GET localhost:8081/departments
    public ResponseEntity<List<DepartmentDto>> getAll() {
        List<DepartmentDto> departmentDtos = departmentService.findAll();
        if (departmentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(departmentDtos);
        }
    }

    @GetMapping("/{id}") // GET localhost:8081/departments/
    public ResponseEntity<Optional<DepartmentDto>> getById(@PathVariable Long id) {
        Optional<DepartmentDto> departmentDTO = departmentService.findById(id);
        if (departmentDTO.isPresent()) {
            return ResponseEntity.ok(departmentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping //POST localhost:8081/departments
    public ResponseEntity create(@RequestBody DepartmentDto department) {
        if (departmentService.create(department)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}") //PUT localhost:8081/departments/
    public ResponseEntity update(@PathVariable Long id, @RequestBody DepartmentDto newDepartmentDto) {
        if (departmentService.update(id, newDepartmentDto)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}") //DELETE localhost:8081/departments/
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<DepartmentDto> departmentDTO = departmentService.findById(id);
        if (departmentDTO.isPresent()) {
            departmentService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
