package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.SprintDTO;
import com.example.teamsFlow.api.dto.TaskDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Project;
import com.example.teamsFlow.model.entity.Sprint;
import com.example.teamsFlow.model.entity.Task;
import com.example.teamsFlow.service.ProjectService;
import com.example.teamsFlow.service.SprintService;
import com.example.teamsFlow.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
@CrossOrigin
public class SprintController {

    private final SprintService service;
    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity get() {
        List<Sprint> sprints = service.getSprints();
        return ResponseEntity.ok(sprints.stream().map(SprintDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Sprint> sprint = service.getSprintById(id);
        if (!sprint.isPresent()) {
            return new ResponseEntity("Sprint não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sprint.map(SprintDTO::create));
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity getTasks(@PathVariable("id") Long id) {
        if (!service.getSprintById(id).isPresent()) {
            return new ResponseEntity("Sprint não encontrada", HttpStatus.NOT_FOUND);
        }
        List<Task> tasks = taskService.getTasksBySprint(id);
        return ResponseEntity.ok(tasks.stream().map(TaskDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody SprintDTO dto) {
        try {
            Sprint sprint = converter(dto);
            sprint = service.salvar(sprint);
            return new ResponseEntity(sprint, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SprintDTO dto) {
        if (!service.getSprintById(id).isPresent()) {
            return new ResponseEntity("Sprint não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Sprint sprint = converter(dto);
            sprint.setId(id);
            service.salvar(sprint);
            return ResponseEntity.ok(sprint);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Sprint> sprint = service.getSprintById(id);
        if (!sprint.isPresent()) {
            return new ResponseEntity("Sprint não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sprint.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Sprint converter(SprintDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sprint sprint = modelMapper.map(dto, Sprint.class);
        if (dto.getProjectId() != null) {
            Optional<Project> project = projectService.getProjectById(dto.getProjectId());
            sprint.setProject(project.orElse(null));
        }
        return sprint;
    }
}
