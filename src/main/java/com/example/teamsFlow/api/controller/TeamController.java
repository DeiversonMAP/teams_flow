package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.TeamDTO;
import com.example.teamsFlow.model.entity.Team;
import com.example.teamsFlow.model.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<TeamDTO> teams = teamRepository.findAll()
                .stream()
                .map(team -> modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(teams);
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO) {
        Team team = modelMapper.map(teamDTO, Team.class);
        Team savedTeam = teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedTeam, TeamDTO.class));
    }
}