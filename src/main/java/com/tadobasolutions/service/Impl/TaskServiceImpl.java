package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.request.TaskRequestDTO;
import com.tadobasolutions.dto.response.TaskResponseDTO;
import com.tadobasolutions.entity.Employee;
import com.tadobasolutions.entity.Task;
import com.tadobasolutions.entity.enums.TaskStatus;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.EmployeeRepository;
import com.tadobasolutions.repository.TaskRepository;
import com.tadobasolutions.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
                task.getUpdatedAt()
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
        task.setAssignedDate(LocalDate.now());
        task.setEmployee(employee);

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
        return taskRepository.findTasksByFilters(status, employeeId, targetDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public TaskResponseDTO submitTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        LocalDate today = LocalDate.now();
        task.setSubmissionDate(today);

        if (!today.isAfter(task.getTargetDate())) {
            task.setStatus(TaskStatus.COMPLETED); // on or before target date
        } else {
            task.setStatus(TaskStatus.LATE); // after target date
        }

        return mapToDTO(taskRepository.save(task));
    }

}