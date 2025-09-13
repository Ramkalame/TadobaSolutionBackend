package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.UserDTO;
import com.tadobasolutions.dto.UserProfileDTO;
import com.tadobasolutions.entity.Department;
import com.tadobasolutions.entity.TaskStatus;
import com.tadobasolutions.entity.User;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.DepartmentRepository;
import com.tadobasolutions.repository.TaskRepository;
import com.tadobasolutions.repository.UserRepository;
import com.tadobasolutions.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO getCurrentUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.setId(user.getId());
        profileDTO.setUsername(user.getUsername());
        profileDTO.setEmail(user.getEmail());

        if (user.getDepartment() != null) {
            profileDTO.setDepartmentName(user.getDepartment().getName());
        }

        if (user.getManager() != null) {
            profileDTO.setManagerName(user.getManager().getUsername());
        }

        // Get task statistics
        Long totalTasks = (long) taskRepository.findByAssignedToId(user.getId()).size();
        Long completedTasks = (long) taskRepository.findByAssignedToIdAndStatus(user.getId(), TaskStatus.COMPLETED).size();
        Long pendingTasks = totalTasks - completedTasks;

        profileDTO.setTaskCount(totalTasks);
        profileDTO.setCompletedTaskCount(completedTasks);
        profileDTO.setPendingTaskCount(pendingTasks);

        return profileDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getEmployeesByManager(Long managerId) {
        return userRepository.findEmployeesByManagerId(managerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getEmployeesByDepartmentAndManager(Long departmentId, Long managerId) {
        return userRepository.findByDepartmentIdAndManagerId(departmentId, managerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (userDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(userDTO.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + userDTO.getDepartmentId()));
            user.setDepartment(department);
        }

        if (userDTO.getManagerId() != null) {
            User manager = userRepository.findById(userDTO.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + userDTO.getManagerId()));
            user.setManager(manager);
        }

        user.setActive(userDTO.isActive());
        User updatedUser = userRepository.save(user);

        return convertToDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void processPasswordResetToken(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid reset token"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reset token has expired");
        }

        user.setPassword(encoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void generatePasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        // In a real application, you would send an email with the reset token
        // emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        dto.setCreatedAt(user.getCreatedAt());

        // Set role (assuming first role)
        if (!user.getRoles().isEmpty()) {
            dto.setRole(user.getRoles().iterator().next().getName().name());
        }

        if (user.getDepartment() != null) {
            dto.setDepartmentId(user.getDepartment().getId());
            dto.setDepartmentName(user.getDepartment().getName());
        }

        if (user.getManager() != null) {
            dto.setManagerId(user.getManager().getId());
            dto.setManagerName(user.getManager().getUsername());
        }

        return dto;
    }
}
