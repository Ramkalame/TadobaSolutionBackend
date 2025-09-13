package com.tadobasolutions.controller;

import com.tadobasolutions.dto.CreateTaskRequest;
import com.tadobasolutions.dto.TaskDTO;
import com.tadobasolutions.dto.TaskStatusRequest;
import com.tadobasolutions.dto.UpdateTaskRequest;
import com.tadobasolutions.entity.TaskStatus;
import com.tadobasolutions.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TaskDTO> createTask(@RequestBody CreateTaskRequest taskRequest) {
        // In a real app, you would get the manager ID from the authenticated user
        Long managerId = 1L; // This should be retrieved from the authenticated user
        TaskDTO task = taskService.createTask(taskRequest, managerId);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
        TaskDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasksByUser() {
        // In a real app, you would get the user ID from the authenticated user
        Long userId = 2L; // This should be retrieved from the authenticated user
        List<TaskDTO> tasks = taskService.getTasksByUser(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<TaskDTO>> getTasksByManager() {
        // In a real app, you would get the manager ID from the authenticated user
        Long managerId = 1L; // This should be retrieved from the authenticated user
        List<TaskDTO> tasks = taskService.getTasksByManager(managerId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable String status) {
        // In a real app, you would get the user ID from the authenticated user
        Long userId = 2L; // This should be retrieved from the authenticated user
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        List<TaskDTO> tasks = taskService.getTasksByStatus(userId, taskStatus);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<TaskDTO>> getTasksByDepartmentAndManager(@PathVariable Long departmentId) {
        // In a real app, you would get the manager ID from the authenticated user
        Long managerId = 1L; // This should be retrieved from the authenticated user
        List<TaskDTO> tasks = taskService.getTasksByDepartmentAndManager(departmentId, managerId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody UpdateTaskRequest taskRequest) {
        TaskDTO task = taskService.updateTask(taskId, taskRequest);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(@PathVariable Long taskId, @RequestBody TaskStatusRequest statusRequest) {
        TaskDTO task = taskService.updateTaskStatus(taskId, statusRequest);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }
}