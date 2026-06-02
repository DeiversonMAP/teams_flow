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
    private TaskStatus currentStatus;
    private Integer storyPoints;

    @ManyToOne
    @JoinColumn(name = "created_by_id_id")
    private User createdById;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id_id")
    private User assignedToId;

    @ManyToOne
    @JoinColumn(name = "tested_by_id_id")
    private User testedById;

    public void assignTo(User user) {
        this.assignedToId = user;
    }

    public void changeStatus(TaskStatus newStatus) {
        this.currentStatus = newStatus;
    }

    public boolean isCompleted() {
        return this.currentStatus == TaskStatus.DONE;
    }

}
