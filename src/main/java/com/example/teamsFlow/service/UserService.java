package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.model.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Transactional
    public User salvar(User user) {
        validar(user);
        return repository.save(user);
    }

    @Transactional
    public void excluir(User user) {
        Objects.requireNonNull(user.getId());
        repository.delete(user);
    }

    public void validar(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new RegraNegocioException("Email inválido");
        }
        if (user.getRole() == null) {
            throw new RegraNegocioException("Role inválido");
        }
    }
}
