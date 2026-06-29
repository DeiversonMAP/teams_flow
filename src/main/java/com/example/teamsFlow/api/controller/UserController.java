package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.UserDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Users", description = "Gerenciamento de usuários")
public class UserController {
    private final UserService service;

    @GetMapping
    @Operation(summary = "Listar usuários")
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getUsers().stream().map(UserDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<User> u = service.getUserById(id);
        if (!u.isPresent()) return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(u.map(UserDTO::create));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody UserDTO dto) {
        if (!service.getUserById(id).isPresent()) return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        try {
            User u = new ModelMapper().map(dto, User.class); u.setId(id);
            service.salvar(u); return ResponseEntity.ok(u);
        } catch (RegraNegocioException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário")
    public ResponseEntity excluir(@PathVariable Long id) {
        Optional<User> u = service.getUserById(id);
        if (!u.isPresent()) return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        service.excluir(u.get()); return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
