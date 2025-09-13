package com.tadobasolutions.dto;

import com.tadobasolutions.entity.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Long assignedById;
    private String assignedByName;
    private Long assignedToId;
    private String assignedToName;
    private LocalDateTime assignedDate;
    private LocalDateTime dueDate;
    private LocalDateTime completedDate;
    private LocalDateTime submittedDate;
    private LocalDateTime createdAt;
}
