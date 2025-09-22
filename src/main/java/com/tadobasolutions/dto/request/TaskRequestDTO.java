package com.tadobasolutions.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {
    private String task;
    private String description;
    private LocalDate targetDate;
    private LocalDate submissionDate;
    private Long employeeId;
    private LocalDate assignedDate;
}
