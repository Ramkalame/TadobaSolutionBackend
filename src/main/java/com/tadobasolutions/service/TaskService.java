package com.tadobasolutions.service;

import com.tadobasolutions.dto.TaskStatusCountDTO;
import com.tadobasolutions.dto.request.TaskRequestDTO;
import com.tadobasolutions.dto.response.ReportSummaryDTO;
import com.tadobasolutions.dto.response.TaskResponseDTO;
import com.tadobasolutions.entity.enums.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO dto);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO dto);

    void deleteTask(Long id);

    TaskResponseDTO getTaskById(Long id);

    List<TaskResponseDTO> getAllTasks(TaskStatus status, Long employeeId, LocalDate targetDate);

    TaskResponseDTO submitTask(Long id);

    TaskStatusCountDTO getTaskStatusCounts(Long employeeId);

    ReportSummaryDTO getMonthlyReportSummary(Long employeeId, int month, int year);

    byte[] exportMonthlyReportExcel(Long employeeId, int month, int year);

    String getEmployeeNameById(Long id);

}
