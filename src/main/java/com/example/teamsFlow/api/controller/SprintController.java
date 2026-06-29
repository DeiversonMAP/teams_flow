package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.SprintDTO;
import com.example.teamsFlow.api.dto.TaskDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Sprint;
import com.example.teamsFlow.service.ProjectService;
import com.example.teamsFlow.service.SprintService;
import com.example.teamsFlow.service.TaskService;
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
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Sprints", description = "Gerenciamento de sprints")
public class SprintController {
    private final SprintService service;
    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Listar sprints")
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getSprints().stream().map(SprintDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sprint por ID")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Sprint> s = service.getSprintById(id);
        if (!s.isPresent()) return new ResponseEntity("Sprint não encontrada", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(s.map(SprintDTO::create));
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Listar tasks da sprint")
    public ResponseEntity getTasks(@PathVariable Long id) {
        if (!service.getSprintById(id).isPresent()) return new ResponseEntity("Sprint não encontrada", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(taskService.getTasksBySprint(id).stream().map(TaskDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    @Operation(summary = "Criar sprint")
    public ResponseEntity post(@RequestBody SprintDTO dto) {
        try {
            Sprint s = new ModelMapper().map(dto, Sprint.class);
            if (dto.getProjectId() != null) s.setProject(projectService.getProjectById(dto.getProjectId()).orElse(null));
            s = service.salvar(s);
            return new ResponseEntity(s, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar sprint")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody SprintDTO dto) {
        if (!service.getSprintById(id).isPresent()) return new ResponseEntity("Sprint não encontrada", HttpStatus.NOT_FOUND);
        try {
            Sprint s = new ModelMapper().map(dto, Sprint.class);
            if (dto.getProjectId() != null) s.setProject(projectService.getProjectById(dto.getProjectId()).orElse(null));
            s.setId(id); service.salvar(s); return ResponseEntity.ok(s);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir sprint")
    public ResponseEntity excluir(@PathVariable Long id) {
        Optional<Sprint> s = service.getSprintById(id);
        if (!s.isPresent()) return new ResponseEntity("Sprint não encontrada", HttpStatus.NOT_FOUND);
        service.excluir(s.get()); return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
