package com.example.teamsFlow.model.api.dto;

import com.example.teamsFlow.model.enuns.TaskStatus;
import com.example.teamsFlow.model.enuns.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransitionRuleDTO {
    private Long id;
    private TaskStatus fromStatus; // Enum
    private TaskStatus toStatus; // Enum
    private UserRole requiredRole; // Enum
    private String description;
    private Long boardId;
}
