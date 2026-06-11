package com.example.teamsFlow.model.repository;

import com.example.teamsFlow.model.entity.Task;
import com.example.teamsFlow.model.enuns.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findBySprint_Id(Long sprintId);
    List<Task> findByAssignedTo_Id(Long userId);
    List<Task> findByCurrentStatus(TaskStatus status);
}
