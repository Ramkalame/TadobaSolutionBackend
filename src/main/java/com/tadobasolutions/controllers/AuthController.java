package com.tadobasolutions.controllers;

import com.tadobasolutions.dto.LoginDTO;
import com.tadobasolutions.service.AuthService;
import com.tadobasolutions.utilities.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginDTO>> login(@RequestBody LoginDTO dto) {
        LoginDTO loggedIn = authService.login(dto);
        return ResponseEntity.ok(
                ApiResponse.<LoginDTO>builder()
                        .data(loggedIn)
                        .message("Login successful")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }
}
