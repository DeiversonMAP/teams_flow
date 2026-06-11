package com.example.teamsFlow.api.controller;

import com.example.teamsFlow.api.dto.UserDTO;
import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(modelMapper.map(user, UserDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Nota: Em um cenário real, você criaria as subclasses (Administrator, Leader, Member)
    // dependendo do 'role' ou usando endpoints específicos.
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedUser, UserDTO.class));
    }
}