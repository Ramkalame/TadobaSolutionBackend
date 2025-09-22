package com.tadobasolutions.service;

import com.tadobasolutions.dto.DepartmentDTO;
import com.tadobasolutions.entity.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(String name);

    DepartmentDTO updateDepartment(Long id, String name);

    void deleteDepartment(Long id);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();
}
