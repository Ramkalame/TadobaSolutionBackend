package com.tadobasolutions.repository;

import com.tadobasolutions.entity.Task;
import com.tadobasolutions.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToId(Long userId);

    List<Task> findByAssignedById(Long managerId);

    List<Task> findByAssignedToIdAndStatus(Long userId, TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId AND t.status IN :statuses")
    List<Task> findByAssignedToIdAndStatusIn(@Param("userId") Long userId,
                                             @Param("statuses") List<TaskStatus> statuses);

    @Query("SELECT t FROM Task t WHERE t.assignedBy.id = :managerId AND t.assignedTo.department.id = :departmentId")
    List<Task> findByManagerIdAndDepartmentId(@Param("managerId") Long managerId,
                                              @Param("departmentId") Long departmentId);

    @Query("SELECT t FROM Task t WHERE t.assignedBy.id = :managerId AND t.status = :status")
    List<Task> findByManagerIdAndStatus(@Param("managerId") Long managerId,
                                        @Param("status") TaskStatus status);

    List<Task> findByDueDateBeforeAndStatusNot(LocalDateTime dueDate, TaskStatus status);
}