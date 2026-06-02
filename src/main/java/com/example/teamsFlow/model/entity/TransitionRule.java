package com.example.teamsFlow.model.entity;

import com.example.teamsFlow.model.enuns.TaskStatus;
import com.example.teamsFlow.model.enuns.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransitionRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TaskStatus fromStatus;
    private TaskStatus toStatus;
    private UserRole requiredRole;
    private String description;

    public boolean allows(TaskStatus from, TaskStatus to) {
        return fromStatus == from && toStatus == to;
    }


}
