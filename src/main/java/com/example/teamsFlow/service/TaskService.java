package com.example.teamsFlow.service;
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
    public TaskService(TaskRepository repository) { this.repository = repository; }
    public List<Task> getTasks() { return repository.findAll(); }
    public Optional<Task> getTaskById(Long id) { return repository.findById(id); }
    public List<Task> getTasksBySprint(Long sprintId) { return repository.findBySprint_Id(sprintId); }
    public List<Task> getTasksByAssignedUser(Long userId) { return repository.findByAssignedTo_Id(userId); }
    public List<Task> getTasksByStatus(TaskStatus status) { return repository.findByCurrentStatus(status); }
    @Transactional
    public Task salvar(Task entity) { return repository.save(entity); }
    @Transactional
    public void excluir(Task entity) { Objects.requireNonNull(entity.getId()); repository.delete(entity); }
}
