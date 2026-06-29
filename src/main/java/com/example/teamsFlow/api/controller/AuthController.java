package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.CredenciaisDTO;
import com.example.teamsFlow.api.dto.TokenDTO;
import com.example.teamsFlow.api.dto.UserDTO;
import com.example.teamsFlow.exception.SenhaInvalidaException;
import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.security.JwtService;
import com.example.teamsFlow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Auth", description = "Autenticação e registro de usuários")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica o usuário e retorna um token JWT")
    public ResponseEntity<TokenDTO> login(@RequestBody CredenciaisDTO credenciais) {
        try {
            User user = new User();
            user.setEmail(credenciais.getEmail());
            user.setPasswordHash(credenciais.getSenha());
            userService.autenticar(user);
            User userEntity = userService.getUserByEmail(credenciais.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
            String token = jwtService.gerarToken(userEntity);
            return ResponseEntity.ok(new TokenDTO(credenciais.getEmail(), token));
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuário", description = "Cria um novo usuário com senha criptografada")
    public ResponseEntity register(@RequestBody UserDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank())
            return ResponseEntity.badRequest().body("Email inválido");
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setCreatedAt(dto.getCreatedAt());
        user.setPasswordHash(passwordEncoder.encode(dto.getPasswordHash()));
        user = userService.salvar(user);
        return new ResponseEntity<>(UserDTO.create(user), HttpStatus.CREATED);
    }
}
