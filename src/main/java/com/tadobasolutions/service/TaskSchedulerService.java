package com.tadobasolutions.service;

import com.tadobasolutions.entity.Task;
import com.tadobasolutions.entity.enums.TaskStatus;
import com.tadobasolutions.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskSchedulerService {

    private final TaskRepository taskRepository;

    // Runs every day at 1 AM
    @Scheduled(cron = "0 0 1 * * ?")
    public void markOverdueTasks() {
        LocalDate today = LocalDate.now();
        List<Task> overdueTasks = taskRepository.findAll().stream()
                .filter(task -> task.getStatus() == TaskStatus.PENDING &&
                        task.getTargetDate().isBefore(today))
                .toList();

        if (!overdueTasks.isEmpty()) {
            overdueTasks.forEach(task -> task.setStatus(TaskStatus.OVERDUE));
            taskRepository.saveAll(overdueTasks);
            log.info("Marked {} tasks as OVERDUE", overdueTasks.size());
        }
    }
}