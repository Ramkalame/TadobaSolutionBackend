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

    @Query("""
                SELECT 
                    COUNT(t) as total,
                    SUM(CASE WHEN t.status = 'COMPLETED' THEN 1 ELSE 0 END) as completed,
                    SUM(CASE WHEN t.status = 'PENDING' THEN 1 ELSE 0 END) as pending,
                    SUM(CASE WHEN t.status = 'LATE' THEN 1 ELSE 0 END) as late,
                    SUM(CASE WHEN t.status = 'OVERDUE' THEN 1 ELSE 0 END) as overdue
                FROM Task t
                WHERE (:employeeId IS NULL OR t.employee.id = :employeeId)
            """)
    Object getTaskStatusCounts(@Param("employeeId") Long employeeId);

}
