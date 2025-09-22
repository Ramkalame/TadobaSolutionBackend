package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.request.EmployeeRequestDTO;
import com.tadobasolutions.dto.response.EmployeeResponseDTO;
import com.tadobasolutions.entity.Department;
import com.tadobasolutions.entity.Employee;
import com.tadobasolutions.exception.BadRequestException;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.DepartmentRepository;
import com.tadobasolutions.repository.EmployeeRepository;
import com.tadobasolutions.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private EmployeeResponseDTO mapToDTO(Employee emp) {
        return new EmployeeResponseDTO(
                emp.getId(),
                emp.getName(),
                emp.getEmail(),
                emp.getDepartment().getId(),
                emp.getDepartment().getName(),
                emp.getDob(),
                emp.getResponsibilities(),
                emp.getRole(),
                emp.getCreatedAt(),
                emp.getUpdatedAt()
        );
    }

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        if (employeeRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Employee with this email already exists");
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Employee emp = new Employee();
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setPassword(dto.getPassword());
        emp.setDepartment(department);
        emp.setDob(dto.getDob());
        emp.setResponsibilities(dto.getResponsibilities());

        return mapToDTO(employeeRepository.save(emp));
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            emp.setName(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            emp.setEmail(dto.getEmail());
        }
        if (dto.getDob() != null) {
            emp.setDob(dto.getDob());
        }
        if (dto.getResponsibilities() != null) {
            emp.setResponsibilities(dto.getResponsibilities());
        }
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            emp.setDepartment(department);
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            emp.setPassword(dto.getPassword());
        }

        return mapToDTO(employeeRepository.save(emp));
    }


    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return mapToDTO(emp);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
