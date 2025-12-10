package com.tadobasolutions.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {
    private String task;
    private String description;
    private LocalDateTime targetDate;
    private LocalDateTime submissionDate;
    private Long employeeId;
    private Integer empRating;
    private Integer adminRating;
    private String adminRemarks;
}
