package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.model.entity.Sprint;
import com.example.teamsFlow.model.repository.SprintRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {

//    @Autowired
//    private SprintRepository sprintRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    // Buscar todas as sprints de um projeto específico
//    @GetMapping("/project/{projectId}")
//    public ResponseEntity<List<SprintDTO>> getSprintsByProject(@PathVariable Long projectId) {
//        List<SprintDTO> sprints = sprintRepository.findByProjectId(projectId)
//                .stream()
//                .map(sprint -> modelMapper.map(sprint, SprintDTO.class))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(sprints);
//    }
//
//    @PostMapping
//    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintDTO sprintDTO) {
//        Sprint sprint = modelMapper.map(sprintDTO, Sprint.class);
//        Sprint savedSprint = sprintRepository.save(sprint);
//        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedSprint, SprintDTO.class));
//    }
}