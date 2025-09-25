package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.ChangePasswordDTO;
import com.tadobasolutions.dto.LoginDTO;
import com.tadobasolutions.entity.Admin;
import com.tadobasolutions.entity.Employee;
import com.tadobasolutions.entity.enums.Role;
import com.tadobasolutions.exception.BadRequestException;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.AdminRepository;
import com.tadobasolutions.repository.EmployeeRepository;
import com.tadobasolutions.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public LoginDTO login(LoginDTO dto) {
        if (dto.getUserType() == null) {
            throw new BadRequestException("User type is required");
        }

        switch (dto.getUserType()) {
            case ADMIN -> {
                Admin admin = adminRepository.findByEmail(dto.getEmail())
                        .orElseThrow(() -> new ResourceNotFoundException("Incorrect Username or email"));
                if (!admin.getPassword().equals(dto.getPassword())) {
                    throw new BadRequestException("Incorrect Password");
                }
                return new LoginDTO(Role.ADMIN, admin.getEmail(), null, admin.getId(), admin.getName());
            }
            case EMPLOYEE -> {
                Employee emp = employeeRepository.findByEmail(dto.getEmail())
                        .orElseThrow(() -> new ResourceNotFoundException("Incorrect Username or email"));
                if (!emp.getPassword().equals(dto.getPassword())) {
                    throw new BadRequestException("Incorrect Password");
                }
                return new LoginDTO(Role.EMPLOYEE, emp.getEmail(), null, emp.getId(), emp.getName());
            }
            default -> throw new BadRequestException("Unsupported user type");
        }
    }

    @Override
    public void changePassword(ChangePasswordDTO dto) {
        if (dto.getUserType() == null) {
            throw new BadRequestException("User type is required");
        }

        switch (dto.getUserType()) {
            case ADMIN -> {
                Admin admin = adminRepository.findById(dto.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                if (!admin.getPassword().equals(dto.getOldPassword())) {
                    throw new BadRequestException("Incorrect old password");
                }
                admin.setPassword(dto.getNewPassword());
                adminRepository.save(admin);
            }
            case EMPLOYEE -> {
                Employee emp = employeeRepository.findById(dto.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                if (!emp.getPassword().equals(dto.getOldPassword())) {
                    throw new BadRequestException("Incorrect old password");
                }
                emp.setPassword(dto.getNewPassword());
                employeeRepository.save(emp);
            }
            default -> throw new BadRequestException("Unsupported user type");
        }
    }

}
