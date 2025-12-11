package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.TaskStatusCountDTO;
import com.tadobasolutions.dto.request.TaskRequestDTO;
import com.tadobasolutions.dto.response.ReportSummaryDTO;
import com.tadobasolutions.dto.response.TaskResponseDTO;
import com.tadobasolutions.entity.Employee;
import com.tadobasolutions.entity.Task;
import com.tadobasolutions.entity.enums.TaskStatus;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.EmployeeRepository;
import com.tadobasolutions.repository.TaskRepository;
import com.tadobasolutions.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    private TaskResponseDTO mapToDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTask(),
                task.getDescription(),
                task.getTargetDate(),
                task.getSubmissionDate(),
                task.getStatus(),
                task.getEmployee().getId(),
                task.getEmployee().getName(),
                task.getAssignedDate(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getEmpRating(),
                task.getAdminRating(),
                task.getAdminRemarks()
        );
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Task task = new Task();
        task.setTask(dto.getTask());
        task.setDescription(dto.getDescription());
        task.setTargetDate(dto.getTargetDate());
        task.setAssignedDate(LocalDateTime.now());
        task.setEmployee(employee);
        task.setEmpRating(0);
        task.setAdminRating(0);

        return mapToDTO(taskRepository.save(task));
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (dto.getTask() != null && !dto.getTask().isBlank()) {
            task.setTask(dto.getTask());
        }
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getTargetDate() != null) {
            task.setTargetDate(dto.getTargetDate());
        }
        if (dto.getSubmissionDate() != null) {
            task.setSubmissionDate(dto.getSubmissionDate());
        }
        if (dto.getEmpRating() != null) {
            task.setEmpRating(dto.getEmpRating());
        }
        if (dto.getAdminRating() != null) {
            task.setAdminRating(dto.getAdminRating());
        }
        if (dto.getAdminRemarks() != null) {
            task.setAdminRemarks(dto.getAdminRemarks());
        }
        if (dto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            task.setEmployee(employee);
        }

        return mapToDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return mapToDTO(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks(TaskStatus status, Long employeeId, LocalDate targetDate) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (targetDate != null) {
            startDate = targetDate.atStartOfDay();
            endDate = targetDate.plusDays(1).atStartOfDay();
        }

        return taskRepository.findTasksByFilters(status, employeeId, startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public TaskResponseDTO submitTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // Capture current date & time instead of only date
        LocalDateTime now = LocalDateTime.now();
        task.setSubmissionDate(now);

        // Compare full datetime with target datetime
        if (!now.isAfter(task.getTargetDate())) {
            task.setStatus(TaskStatus.COMPLETED); // on or before target date & time
        } else {
            task.setStatus(TaskStatus.LATE); // after target date & time
        }

        return mapToDTO(taskRepository.save(task));
    }


    @Override
    public TaskStatusCountDTO getTaskStatusCounts(Long employeeId) {
        Object result = taskRepository.getTaskStatusCounts(employeeId);

        if (result == null) {
            // no tasks found for employee or empty table
            return new TaskStatusCountDTO(0, 0, 0, 0, 0);
        }

        Object[] row = (Object[]) result;

        return new TaskStatusCountDTO(
                row[0] != null ? ((Number) row[0]).longValue() : 0, // total
                row[1] != null ? ((Number) row[1]).longValue() : 0, // completed
                row[2] != null ? ((Number) row[2]).longValue() : 0, // pending
                row[3] != null ? ((Number) row[3]).longValue() : 0, // late
                row[4] != null ? ((Number) row[4]).longValue() : 0  // overdue
        );
    }

    @Override
    public String getEmployeeNameById(Long id) {
        return employeeRepository.findById(id)
                .map(Employee::getName)
                .orElse("employee");
    }


    @Override
    public ReportSummaryDTO getMonthlyReportSummary(Long employeeId, int month, int year) {

        List<Task> tasks = taskRepository.findTasksForMonthlyReport(employeeId, month, year);

        if (tasks.isEmpty()) {
            return new ReportSummaryDTO(0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        long total = tasks.size();

        long completed = tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.COMPLETED
                        || t.getStatus() == TaskStatus.LATE)
                .count();

        long pending = tasks.stream().filter(t -> t.getStatus() == TaskStatus.PENDING).count();

        long late = tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.LATE)
                .count();

        long overdue = tasks.stream().filter(t -> t.getStatus() == TaskStatus.OVERDUE).count();

        // AVG EMPLOYEE RATING (1 decimal)
        double avgEmpRating = tasks.stream()
                .filter(t -> t.getEmpRating() != null && t.getEmpRating() > 0)
                .mapToInt(Task::getEmpRating)
                .average()
                .orElse(0);

        double avgEmpRatingRounded = BigDecimal.valueOf(avgEmpRating)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();

        // AVG ADMIN RATING (1 decimal)
        double avgAdminRating = tasks.stream()
                .filter(t -> t.getAdminRating() != null && t.getAdminRating() > 0)
                .mapToInt(Task::getAdminRating)
                .average()
                .orElse(0);

        double avgAdminRatingRounded = BigDecimal.valueOf(avgAdminRating)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();

        // WORK PERCENTAGE (whole number)
        int workPercentage = total == 0 ? 0 :
                BigDecimal.valueOf(((double) completed / total) * 100)
                        .setScale(0, RoundingMode.HALF_UP)
                        .intValue();

        // ADMIN SCORE % (whole number)
        int adminScorePercentage = BigDecimal.valueOf((avgAdminRating / 10) * 100)
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();

        return new ReportSummaryDTO(
                total,
                completed,
                pending,
                late,
                overdue,
                avgEmpRatingRounded,
                avgAdminRatingRounded,
                workPercentage,
                adminScorePercentage
        );
    }


    @Override
    public byte[] exportMonthlyReportExcel(Long employeeId, int month, int year) {

        List<Task> tasks = taskRepository.findTasksForMonthlyReport(employeeId, month, year);
        ReportSummaryDTO summary = getMonthlyReportSummary(employeeId, month, year);

        // Get employee name for header + filename
        String employeeName = tasks.isEmpty() ? "Employee" : tasks.get(0).getEmployee().getName();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employee Report");

        // ------------------- CELL STYLES -------------------
        CellStyle headerStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        headerStyle.setFont(boldFont);

        CellStyle greenStyle = workbook.createCellStyle();
        greenStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle yellowStyle = workbook.createCellStyle();
        yellowStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle redStyle = workbook.createCellStyle();
        redStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int rowIndex = 0;

        // ---------------- HEADER --------------------
        Row header1 = sheet.createRow(rowIndex++);
        header1.createCell(0).setCellValue("Employee: " + employeeName);

        Row header2 = sheet.createRow(rowIndex++);
        header2.createCell(0).setCellValue("Month / Year: " + getMonthName(month) + " " + year);

        rowIndex++;

        // ---------------- SUMMARY SECTION --------------------
        Row title = sheet.createRow(rowIndex++);
        title.createCell(0).setCellValue("Monthly Report Summary");
        title.getCell(0).setCellStyle(headerStyle);

        setColoredRow(sheet.createRow(rowIndex++), "Total Tasks: ", summary.getTotalTasks(), headerStyle);
        setColoredRow(sheet.createRow(rowIndex++), "Completed: ", summary.getCompleted(), greenStyle);
        setColoredRow(sheet.createRow(rowIndex++), "Pending: ", summary.getPending(), yellowStyle);
        setColoredRow(sheet.createRow(rowIndex++), "Late: ", summary.getLate(), redStyle);
        setColoredRow(sheet.createRow(rowIndex++), "Overdue: ", summary.getOverdue(), redStyle);
        sheet.createRow(rowIndex++).createCell(0).setCellValue("Avg Emp Rating: " + summary.getAvgEmpRating());
        sheet.createRow(rowIndex++).createCell(0).setCellValue("Avg Admin Rating: " + summary.getAvgAdminRating());

        rowIndex += 2;

        // ---------------- TASK TABLE HEADERS --------------------
        Row tableHeader = sheet.createRow(rowIndex++);
        String[] columns = {
                "S.No", "Title", "Description",
                "Assigned Date",        // 👈 ADDED HERE
                "Target Date",
                "Submission Date", "Status",
                "Emp Rating", "Admin Rating", "Admin Remarks"
        };

        for (int i = 0; i < columns.length; i++) {
            Cell cell = tableHeader.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // ---------------- TASK DATA ROWS --------------------
        int count = 1;
        for (Task t : tasks) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(count++);
            row.createCell(1).setCellValue(t.getTask());
            row.createCell(2).setCellValue(t.getDescription());
            row.createCell(3).setCellValue(formatDateTime(t.getAssignedDate()));
            row.createCell(4).setCellValue(formatDateTime(t.getTargetDate()));
            row.createCell(5).setCellValue(t.getSubmissionDate() != null
                    ? formatDateTime(t.getSubmissionDate())
                    : "");
            row.createCell(6).setCellValue(t.getStatus().toString());
            row.createCell(7).setCellValue(t.getEmpRating() != null ? t.getEmpRating() : 0);
            row.createCell(8).setCellValue(t.getAdminRating() != null ? t.getAdminRating() : 0);
            row.createCell(9).setCellValue(t.getAdminRemarks() != null ? t.getAdminRemarks() : "");
        }

        // ---------------- AUTO SIZE COLUMNS --------------------
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // ---------------- WRITE FILE --------------------
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

// ================= HELPER METHODS ==================

    private void setColoredRow(Row row, String label, long value, CellStyle style) {
        Cell cell = row.createCell(0);
        cell.setCellValue(label + value);
        cell.setCellStyle(style);
    }

    private String getMonthName(int month) {
        return Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")
                .withLocale(Locale.ENGLISH);

        return dateTime
                .atZone(ZoneId.systemDefault())   // Or ZoneId.of("Asia/Kolkata")
                .withZoneSameInstant(ZoneId.of("Asia/Kolkata"))
                .format(formatter);
    }


}