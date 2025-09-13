package com.tadobasolutions.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateTaskRequest {
    @Size(max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    private Long assignedToId;

    private LocalDateTime dueDate;
}
