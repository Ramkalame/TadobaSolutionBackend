package com.tadobasolutions.service;

import com.tadobasolutions.dto.JwtResponse;
import com.tadobasolutions.dto.LoginRequest;
import com.tadobasolutions.dto.SignupRequest;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    void registerUser(SignupRequest signUpRequest);
}
