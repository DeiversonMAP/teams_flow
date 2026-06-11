package com.example.teamsFlow.model.entity;

import com.example.teamsFlow.model.enuns.TaskStatus;
import com.example.teamsFlow.model.enuns.UserRole;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private TaskStatus fromStatus;

    @Enumerated(EnumType.STRING)
    private TaskStatus toStatus;

    @Enumerated(EnumType.STRING)
    private UserRole requiredRole;

    private String description;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private KanbanBoard board;

    public boolean allows(TaskStatus from, TaskStatus to) {
        return fromStatus == from && toStatus == to;
    }
}
