package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.AuditLogDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.AuditLog;
import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.service.AuditLogService;
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
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@CrossOrigin
public class AuditLogController {

    private final AuditLogService service;
    private final UserService userService;

    @GetMapping
    public ResponseEntity get() {
        List<AuditLog> logs = service.getLogs();
        return ResponseEntity.ok(logs.stream().map(AuditLogDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AuditLog> log = service.getLogById(id);
        if (!log.isPresent()) {
            return new ResponseEntity("Log não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(log.map(AuditLogDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody AuditLogDTO dto) {
        try {
            AuditLog log = converter(dto);
            log = service.salvar(log);
            return new ResponseEntity(log, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AuditLog> log = service.getLogById(id);
        if (!log.isPresent()) {
            return new ResponseEntity("Log não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(log.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AuditLog converter(AuditLogDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AuditLog log = modelMapper.map(dto, AuditLog.class);
        if (dto.getChangedById() != null) {
            Optional<User> user = userService.getUserById(dto.getChangedById());
            log.setChangedBy(user.orElse(null));
        }
        return log;
    }
}
