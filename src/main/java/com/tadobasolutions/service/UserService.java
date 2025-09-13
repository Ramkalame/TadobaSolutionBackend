package com.tadobasolutions.service;

import com.tadobasolutions.dto.UserDTO;
import com.tadobasolutions.dto.UserProfileDTO;

import java.util.List;

public interface UserService {
    UserProfileDTO getCurrentUserProfile();
    List<UserDTO> getAllUsers();
    List<UserDTO> getEmployeesByManager(Long managerId);
    List<UserDTO> getEmployeesByDepartmentAndManager(Long departmentId, Long managerId);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    void processPasswordResetToken(String token, String newPassword);
    void generatePasswordResetToken(String email);
}
