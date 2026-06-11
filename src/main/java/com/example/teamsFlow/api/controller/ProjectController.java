package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.ProjectDTO;
import com.example.teamsFlow.api.dto.SprintDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Project;
import com.example.teamsFlow.model.entity.Sprint;
import com.example.teamsFlow.model.entity.Team;
import com.example.teamsFlow.service.ProjectService;
import com.example.teamsFlow.service.SprintService;
import com.example.teamsFlow.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin
public class ProjectController {

    private final ProjectService service;
    private final TeamService teamService;
    private final SprintService sprintService;

    @GetMapping
    public ResponseEntity get() {
        List<Project> projects = service.getProjects();
        return ResponseEntity.ok(projects.stream().map(ProjectDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Project> project = service.getProjectById(id);
        if (!project.isPresent()) {
            return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(project.map(ProjectDTO::create));
    }

    @GetMapping("/{id}/sprints")
    public ResponseEntity getSprints(@PathVariable("id") Long id) {
        if (!service.getProjectById(id).isPresent()) {
            return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        }
        List<Sprint> sprints = sprintService.getSprintsByProject(id);
        return ResponseEntity.ok(sprints.stream().map(SprintDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ProjectDTO dto) {
        try {
            Project project = converter(dto);
            project = service.salvar(project);
            return new ResponseEntity(project, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProjectDTO dto) {
        if (!service.getProjectById(id).isPresent()) {
            return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Project project = converter(dto);
            project.setId(id);
            service.salvar(project);
            return ResponseEntity.ok(project);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Project> project = service.getProjectById(id);
        if (!project.isPresent()) {
            return new ResponseEntity("Projeto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(project.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Project converter(ProjectDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Project project = modelMapper.map(dto, Project.class);
        if (dto.getTeamId() != null) {
            Optional<Team> team = teamService.getTeamById(dto.getTeamId());
            project.setTeam(team.orElse(null));
        }
        return project;
    }
}
