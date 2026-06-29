package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.ProjectDTO;
import com.example.teamsFlow.api.dto.TeamDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Project;
import com.example.teamsFlow.model.entity.Team;
import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.service.ProjectService;
import com.example.teamsFlow.service.TeamService;
import com.example.teamsFlow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Teams", description = "Gerenciamento de equipes")
public class TeamController {
    private final TeamService service;
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Listar equipes")
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getTeams().stream().map(TeamDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar equipe por ID")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Team> team = service.getTeamById(id);
        if (!team.isPresent()) return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(team.map(TeamDTO::create));
    }

    @GetMapping("/{id}/projects")
    @Operation(summary = "Listar projetos da equipe")
    public ResponseEntity getProjects(@PathVariable Long id) {
        if (!service.getTeamById(id).isPresent()) return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(projectService.getProjectsByTeam(id).stream().map(ProjectDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    @Operation(summary = "Criar equipe")
    public ResponseEntity post(@RequestBody TeamDTO dto) {
        try {
            Team team = new ModelMapper().map(dto, Team.class);
            if (dto.getLeaderId() != null) team.setLeader(userService.getUserById(dto.getLeaderId()).orElse(null));
            team = service.salvar(team);
            return new ResponseEntity(team, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar equipe")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody TeamDTO dto) {
        if (!service.getTeamById(id).isPresent()) return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        try {
            Team team = new ModelMapper().map(dto, Team.class);
            if (dto.getLeaderId() != null) team.setLeader(userService.getUserById(dto.getLeaderId()).orElse(null));
            team.setId(id);
            service.salvar(team);
            return ResponseEntity.ok(team);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir equipe")
    public ResponseEntity excluir(@PathVariable Long id) {
        Optional<Team> team = service.getTeamById(id);
        if (!team.isPresent()) return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        service.excluir(team.get());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
