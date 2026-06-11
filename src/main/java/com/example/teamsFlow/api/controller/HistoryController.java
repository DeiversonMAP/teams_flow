package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.AuditLogDTO;
import com.example.teamsFlow.api.dto.StatusTransitionDTO;
import com.example.teamsFlow.model.repository.AuditLogRepository;
import com.example.teamsFlow.model.repository.StatusTransitionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private StatusTransitionRepository transitionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<StatusTransitionDTO>> getTaskHistory(@PathVariable Long taskId) {
        // ATUALIZADO: Ajustado para chamar o método com underscore
        List<StatusTransitionDTO> history = transitionRepository.findByTask_Id(taskId)
                .stream()
                .map(transition -> modelMapper.map(transition, StatusTransitionDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(history);
    }
}
