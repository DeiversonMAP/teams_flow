package com.example.teamsFlow.service;

import com.example.teamsFlow.exception.RegraNegocioException;
import com.example.teamsFlow.exception.SenhaInvalidaException;
import com.example.teamsFlow.model.entity.User;
import com.example.teamsFlow.model.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() { return repository.findAll(); }
    public Optional<User> getUserById(Long id) { return repository.findById(id); }
    public Optional<User> getUserByEmail(String email) { return repository.findByEmail(email); }

    @Transactional
    public User salvar(User user) { validar(user); return repository.save(user); }

    @Transactional
    public void excluir(User user) { Objects.requireNonNull(user.getId()); repository.delete(user); }

    public UserDetails autenticar(User user) {
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        if (passwordEncoder.matches(user.getPasswordHash(), userDetails.getPassword())) return userDetails;
        throw new SenhaInvalidaException("Senha inválida!");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        String[] roles = user.getRole() != null && user.getRole().name().equals("ADMIN")
                ? new String[]{"ADMIN","LEADER","MEMBER"}
                : user.getRole() != null && user.getRole().name().equals("LEADER")
                ? new String[]{"LEADER","MEMBER"} : new String[]{"MEMBER"};
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()).password(user.getPasswordHash()).roles(roles).build();
    }

    public void validar(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) throw new RegraNegocioException("Nome inválido");
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) throw new RegraNegocioException("Email inválido");
        if (user.getRole() == null) throw new RegraNegocioException("Role inválido");
    }
}
