package com.webapp.employeemanager.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.employeemanager.model.Department;
import com.webapp.employeemanager.model.DepartmentDto;
import com.webapp.employeemanager.repository.DepartmentRepository;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentDto convertToDTO (Department department){
        return DepartmentDto.builder()
                .departmentName(department.getDepartmentName())
                .location(department.getLocation())
                .build();
    }
    public Department convertToEntity(DepartmentDto departmentDTO) {
        Department department = new Department();
        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setLocation(departmentDTO.getLocation());
        return department;
    }
    @Override
    public List<DepartmentDto> findAll(){
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<DepartmentDto> findById(Long id){
        Optional<Department> department = departmentRepository.findById(id);
        return department.map(this::convertToDTO);
    }

   @Override
   public boolean create(DepartmentDto department){
        if (!departmentRepository.existsByDepartmentNameAndLocation(department.getDepartmentName(), department.getLocation())) {
            departmentRepository.save(convertToEntity(department));
            return true;
        }
        else return false;
    }
    @Override
    public boolean update(Long id, DepartmentDto newDepartmentDto) {
        Optional<Department> existingDepartmentDto = departmentRepository.findById(id);
        if(existingDepartmentDto.isPresent()){
            Department existingDepartment = existingDepartmentDto.get();

            existingDepartment.setDepartmentName(newDepartmentDto.getDepartmentName());
            existingDepartment.setLocation(newDepartmentDto.getLocation());

           departmentRepository.save(existingDepartment);

            return true;
        }
        else return false;
        }


    @Override
    public void delete(Long id){
        departmentRepository.deleteById(id);
    }
}
