package com.tadobasolutions.controllers;

import com.tadobasolutions.dto.ChangePasswordDTO;
import com.tadobasolutions.dto.LoginDTO;
import com.tadobasolutions.service.AuthService;
import com.tadobasolutions.utilities.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://tadobasolutions.com",
        "https://tadobasolutions.com",
        "http://mis.tadobasolutions.com",
        "https://mis.tadobasolutions.com"
})
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

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody ChangePasswordDTO dto) {
        authService.changePassword(dto);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .message("Password changed successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

}
