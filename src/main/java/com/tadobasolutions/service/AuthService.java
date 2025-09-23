package com.tadobasolutions.service;

import com.tadobasolutions.dto.LoginDTO;

public interface AuthService {
    LoginDTO login(LoginDTO dto);
}