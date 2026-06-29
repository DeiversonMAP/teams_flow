package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.AuditLogDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.AuditLog;
import com.example.teamsFlow.service.AuditLogService;
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
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Audit Logs")
public class AuditLogController {
    private final AuditLogService service;

    @GetMapping
    @Operation(summary = "Listar todos(as) os(as) logs")
    public ResponseEntity get() {
        List<AuditLog> list = service.getLogs();
        return ResponseEntity.ok(list.stream().map(AuditLogDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar log por ID")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AuditLog> obj = service.getLogById(id);
        if (!obj.isPresent()) return new ResponseEntity("log não encontrado(a)", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(obj.map(AuditLogDTO::create));
    }

    @PostMapping
    @Operation(summary = "Criar log")
    public ResponseEntity post(@RequestBody AuditLogDTO dto) {
        try {
            AuditLog obj = new ModelMapper().map(dto, AuditLog.class);
            obj = service.salvar(obj);
            return new ResponseEntity(obj, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar log")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AuditLogDTO dto) {
        if (!service.getLogById(id).isPresent()) return new ResponseEntity("log não encontrado(a)", HttpStatus.NOT_FOUND);
        try {
            AuditLog obj = new ModelMapper().map(dto, AuditLog.class);
            obj.setId(id);
            service.salvar(obj);
            return ResponseEntity.ok(obj);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir log")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AuditLog> obj = service.getLogById(id);
        if (!obj.isPresent()) return new ResponseEntity("log não encontrado(a)", HttpStatus.NOT_FOUND);
        try { service.excluir(obj.get()); return new ResponseEntity(HttpStatus.NO_CONTENT); }
        catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
