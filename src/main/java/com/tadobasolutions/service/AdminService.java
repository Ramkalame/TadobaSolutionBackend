package com.tadobasolutions.service;

import com.tadobasolutions.dto.request.AdminRequestDTO;
import com.tadobasolutions.dto.response.AdminResponseDTO;

import java.util.List;

public interface AdminService {
    AdminResponseDTO createAdmin(AdminRequestDTO dto);

    AdminResponseDTO updateAdmin(Long id, AdminRequestDTO dto);

    void deleteAdmin(Long id);

    AdminResponseDTO getAdminById(Long id);

    List<AdminResponseDTO> getAllAdmins();
}