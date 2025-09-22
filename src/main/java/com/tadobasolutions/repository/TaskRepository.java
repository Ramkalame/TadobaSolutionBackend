package com.tadobasolutions.repository;

import com.tadobasolutions.entity.Task;
import com.tadobasolutions.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByEmployeeId(Long employeeId);

    @Query("SELECT t FROM Task t " +
            "WHERE (:status IS NULL OR t.status = :status) " +
            "AND (:employeeId IS NULL OR t.employee.id = :employeeId) " +
            "AND (:targetDate IS NULL OR t.targetDate = :targetDate) " +
            "ORDER BY t.assignedDate DESC")
    List<Task> findTasksByFilters(@Param("status") TaskStatus status,
                                  @Param("employeeId") Long employeeId,
                                  @Param("targetDate") LocalDate targetDate);

}
