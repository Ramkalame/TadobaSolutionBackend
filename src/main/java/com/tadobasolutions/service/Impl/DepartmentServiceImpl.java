package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.DepartmentDTO;
import com.tadobasolutions.entity.Department;
import com.tadobasolutions.exception.BadRequestException;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.DepartmentRepository;
import com.tadobasolutions.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private DepartmentDTO mapToDTO(Department department) {
        return new DepartmentDTO(department.getId(), department.getName());
    }

    @Override
    public DepartmentDTO createDepartment(String name) {
        if (departmentRepository.findByName(name).isPresent()) {
            throw new BadRequestException("Department with this name already exists");
        }
        Department department = new Department();
        department.setName(name);
        Department saved = departmentRepository.save(department);
        return mapToDTO(saved);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, String name) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        department.setName(name);
        Department updated = departmentRepository.save(department);
        return mapToDTO(updated);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new BadRequestException("Department not found");
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return mapToDTO(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
