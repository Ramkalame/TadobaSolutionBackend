package com.tadobasolutions.service.Impl;

import com.tadobasolutions.entity.Task;
import com.tadobasolutions.entity.enums.TaskStatus;
import com.tadobasolutions.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskSchedulerService {

    private final TaskRepository taskRepository;

    @Scheduled(fixedDelay = 600000)
    public void markOverdueTasks() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        List<Task> overdueTasks = taskRepository.findOverduePendingTasks(TaskStatus.PENDING, now);
        if (!overdueTasks.isEmpty()) {
            overdueTasks.forEach(task -> task.setStatus(TaskStatus.OVERDUE));
            taskRepository.saveAll(overdueTasks);
        }
    }
}