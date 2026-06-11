package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Task;
import com.example.teamsFlow.model.enuns.TaskStatus;
import com.example.teamsFlow.model.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getTasks() {
        return repository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return repository.findById(id);
    }

    public List<Task> getTasksBySprint(Long sprintId) {
        return repository.findBySprint_Id(sprintId);
    }

    public List<Task> getTasksByAssignedUser(Long userId) {
        return repository.findByAssignedTo_Id(userId);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return repository.findByCurrentStatus(status);
    }

    @Transactional
    public Task salvar(Task task) {
        validar(task);
        return repository.save(task);
    }

    @Transactional
    public void excluir(Task task) {
        Objects.requireNonNull(task.getId());
        repository.delete(task);
    }

    public void validar(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new RegraNegocioException("Título da tarefa inválido");
        }
        if (task.getCurrentStatus() == null) {
            throw new RegraNegocioException("Status da tarefa inválido");
        }
        if (task.getSprint() == null || task.getSprint().getId() == null) {
            throw new RegraNegocioException("Sprint da tarefa inválida");
        }
    }
}
