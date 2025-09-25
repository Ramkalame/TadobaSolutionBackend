package com.tadobasolutions.controllers;

import com.tadobasolutions.dto.TaskStatusCountDTO;
import com.tadobasolutions.dto.request.TaskRequestDTO;
import com.tadobasolutions.dto.response.TaskResponseDTO;
import com.tadobasolutions.entity.enums.TaskStatus;
import com.tadobasolutions.service.TaskService;
import com.tadobasolutions.utilities.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://tadobasolutions.com",
        "https://tadobasolutions.com",
        "http://mis.tadobasolutions.com",
        "https://mis.tadobasolutions.com"
})
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponseDTO>> create(@RequestBody TaskRequestDTO dto) {
        TaskResponseDTO created = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<TaskResponseDTO>builder()
                        .data(created)
                        .message("Task created successfully")
                        .statusCode(HttpStatus.CREATED.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> update(@PathVariable Long id, @RequestBody TaskRequestDTO dto) {
        TaskResponseDTO updated = taskService.updateTask(id, dto);
        return ResponseEntity.ok(
                ApiResponse.<TaskResponseDTO>builder()
                        .data(updated)
                        .message("Task updated successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .data(null)
                        .message("Task deleted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> getById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(
                ApiResponse.<TaskResponseDTO>builder()
                        .data(task)
                        .message("Task fetched successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getAll(
            @RequestParam(required = false) TaskStatus taskStatus,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate
    ) {
        List<TaskResponseDTO> tasks = taskService.getAllTasks(taskStatus, employeeId, targetDate);
        return ResponseEntity.ok(
                ApiResponse.<List<TaskResponseDTO>>builder()
                        .data(tasks)
                        .message("Tasks fetched successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }


    @PutMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> submitTask(@PathVariable Long id) {
        TaskResponseDTO submitted = taskService.submitTask(id);
        return ResponseEntity.ok(
                ApiResponse.<TaskResponseDTO>builder()
                        .data(submitted)
                        .message("Task submitted successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/status-counts")
    public ResponseEntity<ApiResponse<TaskStatusCountDTO>> getTaskStatusCounts(
            @RequestParam(required = false) Long employeeId) {

        TaskStatusCountDTO counts = taskService.getTaskStatusCounts(employeeId);

        return ResponseEntity.ok(
                ApiResponse.<TaskStatusCountDTO>builder()
                        .data(counts)
                        .message("Task counts fetched successfully")
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(LocalDateTime.now())
                        .success(true)
                        .build()
        );
    }
}
