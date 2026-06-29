package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.KanbanBoardDTO;
import com.example.teamsFlow.api.dto.KanbanColumnDTO;
import com.example.teamsFlow.api.dto.TransitionRuleDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.KanbanBoard;
import com.example.teamsFlow.service.KanbanBoardService;
import com.example.teamsFlow.service.KanbanColumnService;
import com.example.teamsFlow.service.ProjectService;
import com.example.teamsFlow.service.TransitionRuleService;
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
@RequestMapping("/api/kanban")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Kanban Boards", description = "Boards kanban vinculados a projetos")
public class KanbanBoardController {
    private final KanbanBoardService service;
    private final ProjectService projectService;
    private final KanbanColumnService columnService;
    private final TransitionRuleService ruleService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar board por ID")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<KanbanBoard> b = service.getBoardById(id);
        if (!b.isPresent()) return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(b.map(KanbanBoardDTO::create));
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Buscar board por projeto")
    public ResponseEntity getByProject(@PathVariable Long projectId) {
        Optional<KanbanBoard> b = service.getBoardByProject(projectId);
        if (!b.isPresent()) return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(b.map(KanbanBoardDTO::create));
    }

    @GetMapping("/{id}/columns")
    @Operation(summary = "Listar colunas do board")
    public ResponseEntity getColumns(@PathVariable Long id) {
        if (!service.getBoardById(id).isPresent()) return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(columnService.getColumnsByBoard(id).stream().map(KanbanColumnDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/rules")
    @Operation(summary = "Listar regras do board")
    public ResponseEntity getRules(@PathVariable Long id) {
        if (!service.getBoardById(id).isPresent()) return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(ruleService.getRulesByBoard(id).stream().map(TransitionRuleDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    @Operation(summary = "Criar board")
    public ResponseEntity post(@RequestBody KanbanBoardDTO dto) {
        try {
            KanbanBoard b = new ModelMapper().map(dto, KanbanBoard.class);
            if (dto.getProjectId() != null) b.setProject(projectService.getProjectById(dto.getProjectId()).orElse(null));
            b = service.salvar(b);
            return new ResponseEntity(b, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir board")
    public ResponseEntity excluir(@PathVariable Long id) {
        Optional<KanbanBoard> b = service.getBoardById(id);
        if (!b.isPresent()) return new ResponseEntity("Board não encontrado", HttpStatus.NOT_FOUND);
        service.excluir(b.get()); return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
