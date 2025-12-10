package com.tadobasolutions.dto.response;

import com.tadobasolutions.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    private Long id;
    private String task;
    private String description;
    private LocalDateTime targetDate;
    private LocalDateTime submissionDate;
    private TaskStatus status;
    private Long employeeId;
    private String employeeName;
    private LocalDateTime assignedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer empRating;
    private Integer adminRating;
    private String adminRemarks;
}
