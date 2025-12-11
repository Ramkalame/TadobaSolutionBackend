package com.tadobasolutions.controllers;

import com.tadobasolutions.dto.response.ReportSummaryDTO;
import com.tadobasolutions.service.TaskService;
import com.tadobasolutions.utilities.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@RestController
@RequestMapping("/api/report")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://tadobasolutions.com",
        "https://tadobasolutions.com",
        "http://mis.tadobasolutions.com",
        "https://mis.tadobasolutions.com"
}, exposedHeaders = {"Content-Disposition"})
@RequiredArgsConstructor
public class ReportController {

    private final TaskService taskService;

    @GetMapping("/employee-summary")
    public ResponseEntity<ApiResponse<ReportSummaryDTO>> getMonthlySummary(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {

        ReportSummaryDTO summary = taskService.getMonthlyReportSummary(employeeId, month, year);

        return ResponseEntity.ok(
                ApiResponse.<ReportSummaryDTO>builder()
                        .data(summary)
                        .message("Monthly report summary fetched successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }


    @GetMapping("/employee-excel")
    public ResponseEntity<byte[]> downloadExcel(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {

        // Excel file
        byte[] excelData = taskService.exportMonthlyReportExcel(employeeId, month, year);

        // Employee Name
        String employeeName = taskService.getEmployeeNameById(employeeId);
        String cleanName = employeeName.trim().toLowerCase().replace(" ", "_");

        // Month name
        String monthName = Month.of(month)
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                .toLowerCase();

        // Final File Name
        String filename =
                "employee_report_" + cleanName + "_" + monthName + "_" + year + ".xlsx";

        HttpHeaders headers = new HttpHeaders();

        // Correct Excel MIME
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        ));

        // File download header
        headers.set(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\""
        );

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }

    private String getMonthName(int month) {
        return Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }


}
