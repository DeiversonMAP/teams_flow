package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.KanbanColumnDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.KanbanColumn;
import com.example.teamsFlow.service.KanbanColumnService;
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
@RequestMapping("/api/kanban-columns")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Kanban Columns")
public class KanbanColumnController {
    private final KanbanColumnService service;

    @GetMapping
    @Operation(summary = "Listar todos(as) os(as) colunas")
    public ResponseEntity get() {
        List<KanbanColumn> list = service.getColumns();
        return ResponseEntity.ok(list.stream().map(KanbanColumnDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar coluna por ID")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<KanbanColumn> obj = service.getColumnById(id);
        if (!obj.isPresent()) return new ResponseEntity("coluna não encontrado(a)", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(obj.map(KanbanColumnDTO::create));
    }

    @PostMapping
    @Operation(summary = "Criar coluna")
    public ResponseEntity post(@RequestBody KanbanColumnDTO dto) {
        try {
            KanbanColumn obj = new ModelMapper().map(dto, KanbanColumn.class);
            obj = service.salvar(obj);
            return new ResponseEntity(obj, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar coluna")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody KanbanColumnDTO dto) {
        if (!service.getColumnById(id).isPresent()) return new ResponseEntity("coluna não encontrado(a)", HttpStatus.NOT_FOUND);
        try {
            KanbanColumn obj = new ModelMapper().map(dto, KanbanColumn.class);
            obj.setId(id);
            service.salvar(obj);
            return ResponseEntity.ok(obj);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir coluna")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<KanbanColumn> obj = service.getColumnById(id);
        if (!obj.isPresent()) return new ResponseEntity("coluna não encontrado(a)", HttpStatus.NOT_FOUND);
        try { service.excluir(obj.get()); return new ResponseEntity(HttpStatus.NO_CONTENT); }
        catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
