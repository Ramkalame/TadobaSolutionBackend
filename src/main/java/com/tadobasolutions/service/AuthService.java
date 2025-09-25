package com.tadobasolutions.service;

import com.tadobasolutions.dto.ChangePasswordDTO;
import com.tadobasolutions.dto.LoginDTO;

public interface AuthService {
    LoginDTO login(LoginDTO dto);
    void changePassword(ChangePasswordDTO dto);
}