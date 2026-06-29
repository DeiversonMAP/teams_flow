package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.AuditLogDTO;
import com.example.teamsFlow.api.dto.StatusTransitionDTO;
import com.example.teamsFlow.service.AuditLogService;
import com.example.teamsFlow.service.StatusTransitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "History", description = "Histórico de transições de status das tarefas")
public class HistoryController {
    private final StatusTransitionService transitionService;
    private final AuditLogService auditLogService;

    @GetMapping("/task/{taskId}")
    @Operation(summary = "Histórico de status da tarefa")
    public ResponseEntity getTaskHistory(@PathVariable("taskId") Long taskId) {
        List<StatusTransitionDTO> history = transitionService.getTransitionsByTask(taskId)
                .stream().map(StatusTransitionDTO::create).collect(Collectors.toList());
        return ResponseEntity.ok(history);
    }

    @GetMapping("/audit")
    @Operation(summary = "Audit logs por entidade")
    public ResponseEntity getAuditLogs(@RequestParam String entityType, @RequestParam Long entityId) {
        List<AuditLogDTO> logs = auditLogService.getLogsByEntity(entityType, entityId)
                .stream().map(AuditLogDTO::create).collect(Collectors.toList());
        return ResponseEntity.ok(logs);
    }
}
