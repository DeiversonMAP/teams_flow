package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.TransitionRuleDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.TransitionRule;
import com.example.teamsFlow.service.TransitionRuleService;
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
@RequestMapping("/api/transition-rules")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Transition Rules")
public class TransitionRuleController {
    private final TransitionRuleService service;

    @GetMapping
    @Operation(summary = "Listar todos(as) os(as) regras")
    public ResponseEntity get() {
        List<TransitionRule> list = service.getRules();
        return ResponseEntity.ok(list.stream().map(TransitionRuleDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar regra por ID")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TransitionRule> obj = service.getRuleById(id);
        if (!obj.isPresent()) return new ResponseEntity("regra não encontrado(a)", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(obj.map(TransitionRuleDTO::create));
    }

    @PostMapping
    @Operation(summary = "Criar regra")
    public ResponseEntity post(@RequestBody TransitionRuleDTO dto) {
        try {
            TransitionRule obj = new ModelMapper().map(dto, TransitionRule.class);
            obj = service.salvar(obj);
            return new ResponseEntity(obj, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar regra")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TransitionRuleDTO dto) {
        if (!service.getRuleById(id).isPresent()) return new ResponseEntity("regra não encontrado(a)", HttpStatus.NOT_FOUND);
        try {
            TransitionRule obj = new ModelMapper().map(dto, TransitionRule.class);
            obj.setId(id);
            service.salvar(obj);
            return ResponseEntity.ok(obj);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir regra")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TransitionRule> obj = service.getRuleById(id);
        if (!obj.isPresent()) return new ResponseEntity("regra não encontrado(a)", HttpStatus.NOT_FOUND);
        try { service.excluir(obj.get()); return new ResponseEntity(HttpStatus.NO_CONTENT); }
        catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
