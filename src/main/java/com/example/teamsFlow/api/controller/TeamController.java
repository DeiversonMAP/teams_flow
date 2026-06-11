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
public class TeamController {

    private final TeamService service;
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity get() {
        List<Team> teams = service.getTeams();
        return ResponseEntity.ok(teams.stream().map(TeamDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Team> team = service.getTeamById(id);
        if (!team.isPresent()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(team.map(TeamDTO::create));
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity getProjects(@PathVariable("id") Long id) {
        if (!service.getTeamById(id).isPresent()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        List<Project> projects = projectService.getProjectsByTeam(id);
        return ResponseEntity.ok(projects.stream().map(ProjectDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TeamDTO dto) {
        try {
            Team team = converter(dto);
            team = service.salvar(team);
            return new ResponseEntity(team, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TeamDTO dto) {
        if (!service.getTeamById(id).isPresent()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Team team = converter(dto);
            team.setId(id);
            service.salvar(team);
            return ResponseEntity.ok(team);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Team> team = service.getTeamById(id);
        if (!team.isPresent()) {
            return new ResponseEntity("Equipe não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(team.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Team converter(TeamDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Team team = modelMapper.map(dto, Team.class);
        if (dto.getLeaderId() != null) {
            Optional<User> leader = userService.getUserById(dto.getLeaderId());
            team.setLeader(leader.orElse(null));
        }
        return team;
    }
}
