package com.tadobasolutions.controllers;

import com.tadobasolutions.dto.request.AdminRequestDTO;
import com.tadobasolutions.dto.response.AdminResponseDTO;
import com.tadobasolutions.service.AdminService;
import com.tadobasolutions.utilities.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://tadobasolutions.com",
        "https://tadobasolutions.com",
        "http://mis.tadobasolutions.com",
        "https://mis.tadobasolutions.com"
})
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<ApiResponse<AdminResponseDTO>> create(@RequestBody AdminRequestDTO dto) {
        AdminResponseDTO created = adminService.createAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<AdminResponseDTO>builder()
                        .data(created)
                        .message("Admin created successfully")
                        .statusCode(HttpStatus.CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponseDTO>> update(@PathVariable Long id,
                                                                @RequestBody AdminRequestDTO dto) {
        AdminResponseDTO updated = adminService.updateAdmin(id, dto);
        return ResponseEntity.ok(
                ApiResponse.<AdminResponseDTO>builder()
                        .data(updated)
                        .message("Admin updated successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .data(null)
                        .message("Admin deleted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponseDTO>> getById(@PathVariable Long id) {
        AdminResponseDTO admin = adminService.getAdminById(id);
        return ResponseEntity.ok(
                ApiResponse.<AdminResponseDTO>builder()
                        .data(admin)
                        .message("Admin fetched successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminResponseDTO>>> getAll() {
        List<AdminResponseDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(
                ApiResponse.<List<AdminResponseDTO>>builder()
                        .data(admins)
                        .message("Admins fetched successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }
}