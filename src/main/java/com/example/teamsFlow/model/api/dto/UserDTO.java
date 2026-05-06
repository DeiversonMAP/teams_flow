package com.example.teamsFlow.model.api.dto;

import com.example.teamsFlow.model.enuns.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private String createdAt;
}
