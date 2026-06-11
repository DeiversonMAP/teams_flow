package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.UserDTO;
import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.User;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity get() {
        List<User> users = service.getUsers();
        return ResponseEntity.ok(users.stream().map(UserDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<User> user = service.getUserById(id);
        if (!user.isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user.map(UserDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody UserDTO dto) {
        try {
            User user = converter(dto);
            user = service.salvar(user);
            return new ResponseEntity(user, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
        if (!service.getUserById(id).isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            User user = converter(dto);
            user.setId(id);
            service.salvar(user);
            return ResponseEntity.ok(user);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<User> user = service.getUserById(id);
        if (!user.isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(user.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public User converter(UserDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, User.class);
    }
}
