package com.example.teamsFlow.model.entity;

import com.example.teamsFlow.model.enuns.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //usar Integer e String para datas
    private String name;
    private String email;
    private String passwordHash;
    private String createdAt;
    private UserRole role;
}
