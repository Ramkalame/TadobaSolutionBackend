package com.tadobasolutions.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryDTO {
    private long totalTasks;
    private long completed;
    private long pending;
    private long late;
    private long overdue;
    private double avgEmpRating;
    private double avgAdminRating;
    private double workPercentage;
    private double adminScorePercentage;
}
