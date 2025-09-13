package com.tadobasolutions.service;

import com.tadobasolutions.dto.CreateTaskRequest;
import com.tadobasolutions.dto.TaskDTO;
import com.tadobasolutions.dto.TaskStatusRequest;
import com.tadobasolutions.dto.UpdateTaskRequest;
import com.tadobasolutions.entity.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(CreateTaskRequest taskRequest, Long managerId);
    TaskDTO getTaskById(Long taskId);
    List<TaskDTO> getTasksByUser(Long userId);
    List<TaskDTO> getTasksByManager(Long managerId);
    List<TaskDTO> getTasksByStatus(Long userId, TaskStatus status);
    List<TaskDTO> getTasksByDepartmentAndManager(Long departmentId, Long managerId);
    TaskDTO updateTask(Long taskId, UpdateTaskRequest taskRequest);
    TaskDTO updateTaskStatus(Long taskId, TaskStatusRequest statusRequest);
    void deleteTask(Long taskId);
    void checkOverdueTasks();
}
