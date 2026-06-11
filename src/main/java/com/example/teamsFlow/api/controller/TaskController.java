package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.TaskDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Sprint;
import com.example.teamsFlow.model.entity.Task;
import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.service.SprintService;
import com.example.teamsFlow.service.TaskService;
import com.example.teamsFlow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin
public class TaskController {

    private final TaskService service;
    private final SprintService sprintService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity get() {
        List<Task> tasks = service.getTasks();
        return ResponseEntity.ok(tasks.stream().map(TaskDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Task> task = service.getTaskById(id);
        if (!task.isPresent()) {
            return new ResponseEntity("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(task.map(TaskDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TaskDTO dto) {
        try {
            Task task = converter(dto);
            task = service.salvar(task);
            return new ResponseEntity(task, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TaskDTO dto) {
        if (!service.getTaskById(id).isPresent()) {
            return new ResponseEntity("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Task task = converter(dto);
            task.setId(id);
            service.salvar(task);
            return ResponseEntity.ok(task);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Task> task = service.getTaskById(id);
        if (!task.isPresent()) {
            return new ResponseEntity("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(task.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Task converter(TaskDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Task task = modelMapper.map(dto, Task.class);
        if (dto.getSprintId() != null) {
            Optional<Sprint> sprint = sprintService.getSprintById(dto.getSprintId());
            task.setSprint(sprint.orElse(null));
        }
        if (dto.getCreatedById() != null) {
            Optional<User> user = userService.getUserById(dto.getCreatedById());
            task.setCreatedBy(user.orElse(null));
        }
        if (dto.getAssignedToId() != null) {
            Optional<User> user = userService.getUserById(dto.getAssignedToId());
            task.setAssignedTo(user.orElse(null));
        }
        if (dto.getTestedById() != null) {
            Optional<User> user = userService.getUserById(dto.getTestedById());
            task.setTestedBy(user.orElse(null));
        }
        return task;
    }
}
