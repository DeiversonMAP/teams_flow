package com.example.teamsFlow.model.repository;

import com.example.teamsFlow.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // O Spring gera a query automaticamente: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);
}
