package com.tadobasolutions.controller;

import com.tadobasolutions.dto.UserDTO;
import com.tadobasolutions.dto.UserProfileDTO;
import com.tadobasolutions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile() {
        UserProfileDTO profile = userService.getCurrentUserProfile();
        return ResponseEntity.ok(profile);
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/employees")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<UserDTO>> getEmployeesByManager() {
        // In a real app, you would get the manager ID from the authenticated user
        // For now, using a hardcoded value for demonstration
        Long managerId = 1L; // This should be retrieved from the authenticated user
        List<UserDTO> employees = userService.getEmployeesByManager(managerId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<UserDTO>> getEmployeesByDepartmentAndManager(@PathVariable Long departmentId) {
        // In a real app, you would get the manager ID from the authenticated user
        Long managerId = 1L; // This should be retrieved from the authenticated user
        List<UserDTO> employees = userService.getEmployeesByDepartmentAndManager(departmentId, managerId);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}