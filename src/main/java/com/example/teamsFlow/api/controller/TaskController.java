package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.TaskDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Task;
import com.example.teamsFlow.service.SprintService;
import com.example.teamsFlow.service.TaskService;
import com.example.teamsFlow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Tasks", description = "Gerenciamento de tarefas")
public class TaskController {
    private final TaskService service;
    private final SprintService sprintService;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Listar tarefas")
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getTasks().stream().map(TaskDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa por ID")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Task> t = service.getTaskById(id);
        if (!t.isPresent()) return new ResponseEntity("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(t.map(TaskDTO::create));
    }

    @PostMapping
    @Operation(summary = "Criar tarefa")
    public ResponseEntity post(@RequestBody TaskDTO dto) {
        try {
            Task t = converter(dto);
            t = service.salvar(t);
            return new ResponseEntity(t, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody TaskDTO dto) {
        if (!service.getTaskById(id).isPresent()) return new ResponseEntity("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        try {
            Task t = converter(dto); t.setId(id); service.salvar(t); return ResponseEntity.ok(t);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tarefa")
    public ResponseEntity excluir(@PathVariable Long id) {
        Optional<Task> t = service.getTaskById(id);
        if (!t.isPresent()) return new ResponseEntity("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        service.excluir(t.get()); return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private Task converter(TaskDTO dto) {
        Task t = new ModelMapper().map(dto, Task.class);
        if (dto.getSprintId() != null) t.setSprint(sprintService.getSprintById(dto.getSprintId()).orElse(null));
        if (dto.getCreatedById() != null) t.setCreatedBy(userService.getUserById(dto.getCreatedById()).orElse(null));
        if (dto.getAssignedToId() != null) t.setAssignedTo(userService.getUserById(dto.getAssignedToId()).orElse(null));
        if (dto.getTestedById() != null) t.setTestedBy(userService.getUserById(dto.getTestedById()).orElse(null));
        return t;
    }
}
