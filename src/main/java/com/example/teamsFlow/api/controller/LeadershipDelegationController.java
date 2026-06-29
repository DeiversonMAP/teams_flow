package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.LeadershipDelegationDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.LeadershipDelegation;
import com.example.teamsFlow.service.LeadershipDelegationService;
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
@RequestMapping("/api/delegations")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Leadership Delegations")
public class LeadershipDelegationController {
    private final LeadershipDelegationService service;

    @GetMapping
    @Operation(summary = "Listar todos(as) os(as) delegaçãos")
    public ResponseEntity get() {
        List<LeadershipDelegation> list = service.getDelegations();
        return ResponseEntity.ok(list.stream().map(LeadershipDelegationDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar delegação por ID")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<LeadershipDelegation> obj = service.getDelegationById(id);
        if (!obj.isPresent()) return new ResponseEntity("delegação não encontrado(a)", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(obj.map(LeadershipDelegationDTO::create));
    }

    @PostMapping
    @Operation(summary = "Criar delegação")
    public ResponseEntity post(@RequestBody LeadershipDelegationDTO dto) {
        try {
            LeadershipDelegation obj = new ModelMapper().map(dto, LeadershipDelegation.class);
            obj = service.salvar(obj);
            return new ResponseEntity(obj, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar delegação")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LeadershipDelegationDTO dto) {
        if (!service.getDelegationById(id).isPresent()) return new ResponseEntity("delegação não encontrado(a)", HttpStatus.NOT_FOUND);
        try {
            LeadershipDelegation obj = new ModelMapper().map(dto, LeadershipDelegation.class);
            obj.setId(id);
            service.salvar(obj);
            return ResponseEntity.ok(obj);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir delegação")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<LeadershipDelegation> obj = service.getDelegationById(id);
        if (!obj.isPresent()) return new ResponseEntity("delegação não encontrado(a)", HttpStatus.NOT_FOUND);
        try { service.excluir(obj.get()); return new ResponseEntity(HttpStatus.NO_CONTENT); }
        catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
