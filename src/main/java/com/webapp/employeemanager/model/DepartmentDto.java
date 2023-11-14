package com.webapp.employeemanager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DepartmentDto {

    private String departmentName;
    private String location;
}
