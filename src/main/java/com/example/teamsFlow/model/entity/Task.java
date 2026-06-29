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
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private Integer storyPoints;
    @Enumerated(EnumType.STRING)
    private TaskStatus currentStatus;
    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;
    @ManyToOne
    @JoinColumn(name = "tested_by_id")
    private User testedBy;
}
