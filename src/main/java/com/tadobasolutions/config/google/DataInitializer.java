package com.tadobasolutions.config.google;

import com.tadobasolutions.entity.Department;
import com.tadobasolutions.entity.ERole;
import com.tadobasolutions.entity.Role;
import com.tadobasolutions.repository.DepartmentRepository;
import com.tadobasolutions.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // Create roles
        createRoleIfNotFound(ERole.ROLE_MANAGER);
        createRoleIfNotFound(ERole.ROLE_EMPLOYEE);

        // Create departments
        createDepartmentIfNotFound("IT", "Information Technology Department");
        createDepartmentIfNotFound("HR", "Human Resources Department");
        createDepartmentIfNotFound("Finance", "Finance and Accounting Department");
        createDepartmentIfNotFound("Operations", "Operations Department");

        alreadySetup = true;
    }

    @Transactional
    void createRoleIfNotFound(ERole name) {
        Role role = roleRepository.findByName(name).orElse(null);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
    }

    @Transactional
    void createDepartmentIfNotFound(String name, String description) {
        Department department = departmentRepository.findByName(name).orElse(null);
        if (department == null) {
            department = new Department(name, description);
            departmentRepository.save(department);
        }
    }
}
