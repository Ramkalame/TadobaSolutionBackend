package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.CreateTaskRequest;
import com.tadobasolutions.dto.TaskDTO;
import com.tadobasolutions.dto.TaskStatusRequest;
import com.tadobasolutions.dto.UpdateTaskRequest;
import com.tadobasolutions.entity.Task;
import com.tadobasolutions.entity.TaskStatus;
import com.tadobasolutions.entity.User;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.TaskRepository;
import com.tadobasolutions.repository.UserRepository;
import com.tadobasolutions.service.TaskService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public TaskDTO createTask(CreateTaskRequest taskRequest, Long managerId) {
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + managerId));

        User assignedTo = userRepository.findById(taskRequest.getAssignedToId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + taskRequest.getAssignedToId()));

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setAssignedBy(manager);
        task.setAssignedTo(assignedTo);
        task.setAssignedDate(LocalDateTime.now());
        task.setDueDate(taskRequest.getDueDate());
        task.setStatus(TaskStatus.PENDING);

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        return convertToDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedToId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByManager(Long managerId) {
        return taskRepository.findByAssignedById(managerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByStatus(Long userId, TaskStatus status) {
        return taskRepository.findByAssignedToIdAndStatus(userId, status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByDepartmentAndManager(Long departmentId, Long managerId) {
        return taskRepository.findByManagerIdAndDepartmentId(managerId, departmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long taskId, UpdateTaskRequest taskRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (taskRequest.getTitle() != null) {
            task.setTitle(taskRequest.getTitle());
        }

        if (taskRequest.getDescription() != null) {
            task.setDescription(taskRequest.getDescription());
        }

        if (taskRequest.getAssignedToId() != null) {
            User assignedTo = userRepository.findById(taskRequest.getAssignedToId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + taskRequest.getAssignedToId()));
            task.setAssignedTo(assignedTo);
        }

        if (taskRequest.getDueDate() != null) {
            task.setDueDate(taskRequest.getDueDate());
        }

        Task updatedTask = taskRepository.save(task);
        return convertToDTO(updatedTask);
    }

    @Override
    @Transactional
    public TaskDTO updateTaskStatus(Long taskId, TaskStatusRequest statusRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        try {
            TaskStatus status = TaskStatus.valueOf(statusRequest.getStatus().toUpperCase());
            task.setStatus(status);

            if (status == TaskStatus.COMPLETED) {
                task.setCompletedDate(LocalDateTime.now());
            }

            if (statusRequest.getSubmittedDate() != null) {
                task.setSubmittedDate(statusRequest.getSubmittedDate());
            }

            Task updatedTask = taskRepository.save(task);
            return convertToDTO(updatedTask);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + statusRequest.getStatus());
        }
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        taskRepository.delete(task);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    @Transactional
    public void checkOverdueTasks() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> overdueTasks = taskRepository.findByDueDateBeforeAndStatusNot(now, TaskStatus.COMPLETED);

        for (Task task : overdueTasks) {
            task.setStatus(TaskStatus.OVERDUE);
            taskRepository.save(task);
        }
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setAssignedDate(task.getAssignedDate());
        dto.setDueDate(task.getDueDate());
        dto.setCompletedDate(task.getCompletedDate());
        dto.setSubmittedDate(task.getSubmittedDate());
        dto.setCreatedAt(task.getCreatedAt());

        if (task.getAssignedBy() != null) {
            dto.setAssignedById(task.getAssignedBy().getId());
            dto.setAssignedByName(task.getAssignedBy().getUsername());
        }

        if (task.getAssignedTo() != null) {
            dto.setAssignedToId(task.getAssignedTo().getId());
            dto.setAssignedToName(task.getAssignedTo().getUsername());
        }

        return dto;
    }
}
