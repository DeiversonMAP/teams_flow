package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.MemberDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.Member;
import com.example.teamsFlow.service.MemberService;
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
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Members")
public class MemberController {
    private final MemberService service;

    @GetMapping
    @Operation(summary = "Listar todos(as) os(as) membros")
    public ResponseEntity get() {
        List<Member> list = service.getMembers();
        return ResponseEntity.ok(list.stream().map(MemberDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar membro por ID")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Member> obj = service.getMemberById(id);
        if (!obj.isPresent()) return new ResponseEntity("membro não encontrado(a)", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(obj.map(MemberDTO::create));
    }

    @PostMapping
    @Operation(summary = "Criar membro")
    public ResponseEntity post(@RequestBody MemberDTO dto) {
        try {
            Member obj = new ModelMapper().map(dto, Member.class);
            obj = service.salvar(obj);
            return new ResponseEntity(obj, HttpStatus.CREATED);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar membro")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody MemberDTO dto) {
        if (!service.getMemberById(id).isPresent()) return new ResponseEntity("membro não encontrado(a)", HttpStatus.NOT_FOUND);
        try {
            Member obj = new ModelMapper().map(dto, Member.class);
            obj.setId(id);
            service.salvar(obj);
            return ResponseEntity.ok(obj);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir membro")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Member> obj = service.getMemberById(id);
        if (!obj.isPresent()) return new ResponseEntity("membro não encontrado(a)", HttpStatus.NOT_FOUND);
        try { service.excluir(obj.get()); return new ResponseEntity(HttpStatus.NO_CONTENT); }
        catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
