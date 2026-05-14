package com.example.teamsFlow.model.repository;

import com.example.teamsFlow.model.entity.Task;
import com.example.teamsFlow.model.enuns.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Busca todas as tarefas de uma Sprint
    List<Task> findBySprintId(Long sprintId);

    // Busca tarefas atribuídas a um usuário específico
    List<Task> findByAssignedToId(Long userId);

    // Busca tarefas por status
    List<Task> findByCurrentStatus(TaskStatus status);
}