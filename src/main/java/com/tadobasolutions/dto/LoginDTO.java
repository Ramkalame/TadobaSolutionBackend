package com.tadobasolutions.dto;

import com.tadobasolutions.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private Role userType;
    private String email;
    private String password;
    private Long id;
    private String name;
}