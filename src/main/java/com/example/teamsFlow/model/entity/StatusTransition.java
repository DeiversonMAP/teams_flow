package com.example.teamsFlow.model.entity;

import com.example.teamsFlow.model.enuns.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusTransition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TaskStatus fromStatus;
    @Enumerated(EnumType.STRING)
    private TaskStatus toStatus;
    private String startDate;
    private String endDate;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne
    @JoinColumn(name = "changed_by_id")
    private User changedBy;
}
