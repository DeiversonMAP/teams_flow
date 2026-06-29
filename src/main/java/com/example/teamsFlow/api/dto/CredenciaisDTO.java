package com.example.teamsFlow.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CredenciaisDTO {
    private String email;
    private String senha;
}
