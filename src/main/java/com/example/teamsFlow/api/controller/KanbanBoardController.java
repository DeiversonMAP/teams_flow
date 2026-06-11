package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.KanbanBoardDTO;
import com.example.teamsFlow.api.dto.KanbanColumnDTO;
import com.example.teamsFlow.api.dto.TransitionRuleDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.KanbanBoard;
import com.example.teamsFlow.model.entity.KanbanColumn;
import com.example.teamsFlow.model.entity.Project;
import com.example.teamsFlow.model.entity.TransitionRule;
import com.example.teamsFlow.service.KanbanBoardService;
import com.example.teamsFlow.service.KanbanColumnService;
import com.example.teamsFlow.service.ProjectService;
import com.example.teamsFlow.service.TransitionRuleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kanban")
@RequiredArgsConstructor
@CrossOrigin
public class KanbanBoardController {

    private final KanbanBoardService service;
    private final ProjectService projectService;
    private final KanbanColumnService columnService;
    private final TransitionRuleService ruleService;

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<KanbanBoard> board = service.getBoardById(id);
        if (!board.isPresent()) {
            return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(board.map(KanbanBoardDTO::create));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity getByProject(@PathVariable("projectId") Long projectId) {
        Optional<KanbanBoard> board = service.getBoardByProject(projectId);
        if (!board.isPresent()) {
            return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(board.map(KanbanBoardDTO::create));
    }

    @GetMapping("/{id}/columns")
    public ResponseEntity getColumns(@PathVariable("id") Long id) {
        if (!service.getBoardById(id).isPresent()) {
            return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        }
        List<KanbanColumn> columns = columnService.getColumnsByBoard(id);
        return ResponseEntity.ok(columns.stream().map(KanbanColumnDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/rules")
    public ResponseEntity getRules(@PathVariable("id") Long id) {
        if (!service.getBoardById(id).isPresent()) {
            return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        }
        List<TransitionRule> rules = ruleService.getRulesByBoard(id);
        return ResponseEntity.ok(rules.stream().map(TransitionRuleDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody KanbanBoardDTO dto) {
        try {
            KanbanBoard board = converter(dto);
            board = service.salvar(board);
            return new ResponseEntity(board, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<KanbanBoard> board = service.getBoardById(id);
        if (!board.isPresent()) {
            return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(board.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public KanbanBoard converter(KanbanBoardDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        KanbanBoard board = modelMapper.map(dto, KanbanBoard.class);
        if (dto.getProjectId() != null) {
            Optional<Project> project = projectService.getProjectById(dto.getProjectId());
            board.setProject(project.orElse(null));
        }
        return board;
    }
}
