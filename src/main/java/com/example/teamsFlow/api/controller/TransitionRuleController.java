package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.TransitionRuleDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.KanbanBoard;
import com.example.teamsFlow.model.entity.TransitionRule;
import com.example.teamsFlow.service.KanbanBoardService;
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
@RequestMapping("/api/transition-rules")
@RequiredArgsConstructor
@CrossOrigin
public class TransitionRuleController {

    private final TransitionRuleService service;
    private final KanbanBoardService boardService;

    @GetMapping
    public ResponseEntity get() {
        List<TransitionRule> rules = service.getRules();
        return ResponseEntity.ok(rules.stream().map(TransitionRuleDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TransitionRule> rule = service.getRuleById(id);
        if (!rule.isPresent()) {
            return new ResponseEntity("Regra não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(rule.map(TransitionRuleDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TransitionRuleDTO dto) {
        try {
            TransitionRule rule = converter(dto);
            rule = service.salvar(rule);
            return new ResponseEntity(rule, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TransitionRuleDTO dto) {
        if (!service.getRuleById(id).isPresent()) {
            return new ResponseEntity("Regra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            TransitionRule rule = converter(dto);
            rule.setId(id);
            service.salvar(rule);
            return ResponseEntity.ok(rule);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TransitionRule> rule = service.getRuleById(id);
        if (!rule.isPresent()) {
            return new ResponseEntity("Regra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(rule.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TransitionRule converter(TransitionRuleDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TransitionRule rule = modelMapper.map(dto, TransitionRule.class);
        if (dto.getBoardId() != null) {
            Optional<KanbanBoard> board = boardService.getBoardById(dto.getBoardId());
            rule.setBoard(board.orElse(null));
        }
        return rule;
    }
}
