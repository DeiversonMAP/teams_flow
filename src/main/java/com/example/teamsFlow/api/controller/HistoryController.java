package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.AuditLogDTO;
import com.example.teamsFlow.api.dto.StatusTransitionDTO;
import com.example.teamsFlow.service.AuditLogService;
import com.example.teamsFlow.service.StatusTransitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
@CrossOrigin
public class HistoryController {

    private final StatusTransitionService transitionService;
    private final AuditLogService auditLogService;

    @GetMapping("/task/{taskId}")
    public ResponseEntity getTaskHistory(@PathVariable("taskId") Long taskId) {
        List<StatusTransitionDTO> history = transitionService.getTransitionsByTask(taskId)
                .stream()
                .map(StatusTransitionDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(history);
    }

    @GetMapping("/audit")
    public ResponseEntity getAuditLogs(
            @RequestParam String entityType,
            @RequestParam Long entityId) {
        List<AuditLogDTO> logs = auditLogService.getLogsByEntity(entityType, entityId)
                .stream()
                .map(AuditLogDTO::create)
                .collect(Collectors.toList());
        return ResponseEntity.ok(logs);
    }
}
