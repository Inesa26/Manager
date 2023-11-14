package com.webapp.employeemanager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Double salary;
    private Department department;

}
