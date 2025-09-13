package com.tadobasolutions.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDTO {
    private Long id;
    private String username;
    private String email;
    private String departmentName;
    private String managerName;
    private Long taskCount;
    private Long completedTaskCount;
    private Long pendingTaskCount;
}