package com.tadobasolutions.dto.request;

import com.tadobasolutions.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {
    private String name;
    private String email;
    private String password;
    private Long departmentId;
    private LocalDate joiningDate;
    private LocalDate dob;
    private String responsibilities;
}