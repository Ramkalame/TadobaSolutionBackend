package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.DepartmentDTO;
import com.tadobasolutions.entity.Department;
import com.tadobasolutions.entity.Employee;
import com.tadobasolutions.exception.BadRequestException;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.DepartmentRepository;
import com.tadobasolutions.repository.EmployeeRepository;
import com.tadobasolutions.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private  final EmployeeRepository employeeRepository;

    private DepartmentDTO mapToDTO(Department department) {
        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getIncharge() != null ? department.getIncharge().getId() : null,
                department.getIncharge() != null ? department.getIncharge().getName() : null
        );
    }

    @Override
    public DepartmentDTO createDepartment(String name) {
        if (departmentRepository.findByName(name).isPresent()) {
            throw new BadRequestException("Department with this name already exists");
        }
        Department department = new Department();
        department.setName(name);
        department.setIncharge(null);
        Department saved = departmentRepository.save(department);
        return mapToDTO(saved);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, String name, Long inchargeId) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        if (name != null) {
            department.setName(name);
        }

        if (inchargeId != null) {
            Employee employee = employeeRepository.findById(inchargeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            department.setIncharge(employee);
        } else {
            department.setIncharge(null);
        }

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
