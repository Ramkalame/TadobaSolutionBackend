package com.tadobasolutions.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Long departmentId;
    private String departmentName;
    private Long managerId;
    private String managerName;
    private boolean active;
    private LocalDateTime createdAt;
}