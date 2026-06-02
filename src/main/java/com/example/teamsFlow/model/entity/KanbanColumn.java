package com.example.teamsFlow.model.entity;

import com.example.teamsFlow.model.enuns.TaskStatus;
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
public class KanbanColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String name;
    private Integer position;

    public boolean matchesStatus(TaskStatus status) {
        return this.status == status;
    }

    public void movePosition(Integer newPosition) {
        this.position = newPosition;
    }
}
