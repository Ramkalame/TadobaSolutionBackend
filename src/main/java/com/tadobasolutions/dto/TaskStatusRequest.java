package com.tadobasolutions.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskStatusRequest {
    private String status;
    private LocalDateTime submittedDate;
}