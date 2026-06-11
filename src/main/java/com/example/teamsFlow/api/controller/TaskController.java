package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.TaskDTO;
import com.example.teamsFlow.model.entity.Task;
import com.example.teamsFlow.model.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    // GET: Listar todas as tarefas
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskRepository.findAll()
                .stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    // GET: Buscar tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok(modelMapper.map(task, TaskDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Criar nova tarefa
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedTask, TaskDTO.class));
    }

    // PUT: Atualizar tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Task task = modelMapper.map(taskDTO, Task.class);
        task.setId(id); // Garante que estamos atualizando o ID correto
        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(modelMapper.map(updatedTask, TaskDTO.class));
    }

    // DELETE: Deletar tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}