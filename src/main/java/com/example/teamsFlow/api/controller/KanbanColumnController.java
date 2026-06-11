package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.KanbanColumnDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.KanbanBoard;
import com.example.teamsFlow.model.entity.KanbanColumn;
import com.example.teamsFlow.service.KanbanBoardService;
import com.example.teamsFlow.service.KanbanColumnService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kanban-columns")
@RequiredArgsConstructor
@CrossOrigin
public class KanbanColumnController {

    private final KanbanColumnService service;
    private final KanbanBoardService boardService;

    @GetMapping
    public ResponseEntity get() {
        List<KanbanColumn> columns = service.getColumns();
        return ResponseEntity.ok(columns.stream().map(KanbanColumnDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<KanbanColumn> column = service.getColumnById(id);
        if (!column.isPresent()) {
            return new ResponseEntity("Coluna não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(column.map(KanbanColumnDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody KanbanColumnDTO dto) {
        try {
            KanbanColumn column = converter(dto);
            column = service.salvar(column);
            return new ResponseEntity(column, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody KanbanColumnDTO dto) {
        if (!service.getColumnById(id).isPresent()) {
            return new ResponseEntity("Coluna não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            KanbanColumn column = converter(dto);
            column.setId(id);
            service.salvar(column);
            return ResponseEntity.ok(column);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<KanbanColumn> column = service.getColumnById(id);
        if (!column.isPresent()) {
            return new ResponseEntity("Coluna não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(column.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public KanbanColumn converter(KanbanColumnDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        KanbanColumn column = modelMapper.map(dto, KanbanColumn.class);
        if (dto.getBoardId() != null) {
            Optional<KanbanBoard> board = boardService.getBoardById(dto.getBoardId());
            column.setBoard(board.orElse(null));
        }
        return column;
    }
}
