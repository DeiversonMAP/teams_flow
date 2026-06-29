package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.ProjectDTO;
import com.example.teamsFlow.api.dto.SprintDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Project;
import com.example.teamsFlow.model.entity.Team;
import com.example.teamsFlow.service.ProjectService;
import com.example.teamsFlow.service.SprintService;
import com.example.teamsFlow.service.TeamService;
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
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Projects", description = "Gerenciamento de projetos")
public class ProjectController {
    private final ProjectService service;
    private final TeamService teamService;
    private final SprintService sprintService;

    @GetMapping
    @Operation(summary = "Listar projetos")
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getProjects().stream().map(ProjectDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar projeto por ID")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Project> p = service.getProjectById(id);
        if (!p.isPresent()) return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(p.map(ProjectDTO::create));
    }

    @GetMapping("/{id}/sprints")
    @Operation(summary = "Listar sprints do projeto")
    public ResponseEntity getSprints(@PathVariable Long id) {
        if (!service.getProjectById(id).isPresent()) return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(sprintService.getSprintsByProject(id).stream().map(SprintDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    @Operation(summary = "Criar projeto")
    public ResponseEntity post(@RequestBody ProjectDTO dto) {
        try {
            Project p = new ModelMapper().map(dto, Project.class);
            if (dto.getTeamId() != null) p.setTeam(teamService.getTeamById(dto.getTeamId()).orElse(null));
            p = service.salvar(p);
            return new ResponseEntity(p, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar projeto")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody ProjectDTO dto) {
        if (!service.getProjectById(id).isPresent()) return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        try {
            Project p = new ModelMapper().map(dto, Project.class);
            if (dto.getTeamId() != null) p.setTeam(teamService.getTeamById(dto.getTeamId()).orElse(null));
            p.setId(id); service.salvar(p); return ResponseEntity.ok(p);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir projeto")
    public ResponseEntity excluir(@PathVariable Long id) {
        Optional<Project> p = service.getProjectById(id);
        if (!p.isPresent()) return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        service.excluir(p.get()); return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
